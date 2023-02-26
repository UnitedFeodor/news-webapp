package by.htp.ex.dao.impl;

import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements IUserDAO	{

	private static final String DB_ID = "id";
	private static final String DB_LOGIN = "login";
	private static final String DB_PASSWORD = "password";

	private static final String DB_ROLE_ID = "roles_id";
	private static final String DB_STATUS_ID = "status_id";

	private static final String DB_ROLES_TITLE = "title";

	private static final int DB_ON_REGISTER_USER_STATUS_ID = 2; // 'active'

	private static final String Q_GET_ALL_USERS = "SELECT * FROM users";
	//private static final String Q_GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? AND password = ?";
	private static final String Q_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";

	private static final String Q_GET_ROLE_ID_BY_TITLE = "SELECT id FROM roles WHERE title = ?";

	// UPDATE users SET status_id = 1 WHERE login = 't';
	private static final String Q_UPDATE_USER_STATUS_ID_BY_LOGIN = "UPDATE users SET status_id = ? WHERE login = ?";
	private static final String Q_UPDATE_USER_STATUS_ID_BY_USER_ID = "UPDATE users SET status_id = ? WHERE id = ?";
	private static final String Q_GET_ROLE_ID_BY_TITLE_AND_STATUS_ID_BY_NAME = "SELECT roles.id,user_status.id FROM roles LEFT JOIN user_status ON user_status.status_name = ? WHERE roles.title = ? ";

	private static final String Q_GET_USER_AND_ROLE_BY_LOGIN = "SELECT users.*, roles.title FROM users LEFT JOIN roles ON users.roles_id = roles.id WHERE login = ?";
	private static final String Q_GET_ID_BY_LOGIN = "SELECT id FROM users WHERE login = ?";
	private static final String Q_GET_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
	private static final String Q_INSERT_USER = "INSERT INTO users (login,password,roles_id,status_id) VALUES (?,?,?,?)";

	private static final String Q_INSERT_USER_DETAILS = "INSERT INTO user_details (users_id,name,surname,birthday) VALUES (?,?,?,?)";

	//private Semaphore sem = new Semaphore(1);
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock writeLock = lock.writeLock();

	@Override
	public boolean signIn(String login, String password) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {

			getUserStatement.setString(1,login);

			try(ResultSet rs = getUserStatement.executeQuery()) {
				if (!rs.isBeforeFirst()) {
					return false; // no such login in db
				}
				rs.next();
				String hashedPassword = rs.getString(DB_PASSWORD);
				return BCrypt.checkpw(password,hashedPassword);

			} catch (IllegalArgumentException e) {
				throw new DaoException(e);
			}


		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}

	}



	@Override
	public String getRole(String login, String password) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {

			getUserStatement.setString(1,login);
			try(ResultSet rs = getUserStatement.executeQuery()) {
				if (!rs.isBeforeFirst() ) {
					throw new DaoException("no user with such login found"); // no such login found in db
				} else {
					rs.next();
					int role_id = rs.getInt(4);
					try(PreparedStatement getRoleStatement = connection.prepareStatement(Q_GET_ROLE_BY_ID)) {
						getRoleStatement.setInt(1,role_id);
						ResultSet rs2 = getRoleStatement.executeQuery();
						rs2.next();
						return rs2.getString(DB_ROLES_TITLE);

					}
				}
			}
		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public User getUserByLogin(String login) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_AND_ROLE_BY_LOGIN)) {

			getUserStatement.setString(1,login);
			try(ResultSet rs = getUserStatement.executeQuery()) {
				User foundUser;
				if (!rs.isBeforeFirst() ) {
					throw new DaoException("no user with such login found"); // no such login found in db
				} else {
					rs.next();
					foundUser = new User(login,rs.getString(DB_PASSWORD));
					foundUser.setId(rs.getInt(DB_ID));
					String role = rs.getString(DB_ROLES_TITLE);
					foundUser.setRole(role);

					return foundUser;
				}
			}


		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public int getIdByLogin(String login) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {

			getUserStatement.setString(1,login);
			try(ResultSet rs = getUserStatement.executeQuery()) {
				if (!rs.isBeforeFirst() ) {
					throw new DaoException("no user with such login found"); // no such login found in db
				} else {
					rs.next();

					return rs.getInt(1);
				}
			}


		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public boolean registration(User user) throws DaoException  {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {

			connection.setAutoCommit(false);

			//sem.acquire();
			writeLock.lock();

			try(PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				getUserStatement.setString(1,user.getEmail());
				try(ResultSet rs = getUserStatement.executeQuery()) {
					if (rs.isBeforeFirst() ) { // such login already found in db
						return false;
					}
				}
			}

			int roleId;
			try(PreparedStatement getRoleIdStatement = connection.prepareStatement(Q_GET_ROLE_ID_BY_TITLE)) {
				//getRoleIdStatement.setString(2,DB_USER_ON_REGISTER_STATUS);
				getRoleIdStatement.setString(1,user.getRole());

				try(ResultSet rs = getRoleIdStatement.executeQuery()) {
					if (!rs.isBeforeFirst()) { // such login found in db
						throw new DaoException("error accessing roles table");
					}
					rs.next();
					if (rs.getObject(1) == null ) {
						throw new DaoException("no role with such title found in db");
					}
					roleId = rs.getInt(1);
				}
			}

			try (PreparedStatement insertUserStatement = connection.prepareStatement(Q_INSERT_USER,Statement.RETURN_GENERATED_KEYS)) {
				insertUserStatement.setString(1,user.getEmail());

				String password = user.getPassword();
				String salt = BCrypt.gensalt();
				String hashedPassword = BCrypt.hashpw(password,salt);

				insertUserStatement.setString(2,hashedPassword);
				insertUserStatement.setInt(3,roleId);
				insertUserStatement.setInt(4,DB_ON_REGISTER_USER_STATUS_ID);
				int modifiedRows = insertUserStatement.executeUpdate();
				if (modifiedRows != 1) {
					throw new DaoException("error inserting user data into the table");
				}

				try (ResultSet generatedKeys = insertUserStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						user.setId(generatedKeys.getInt(1));
					}
					else {
						throw new DaoException("creating user failed, no ID obtained");
					}
					try(PreparedStatement insertDetailsStatement = connection.prepareStatement(Q_INSERT_USER_DETAILS)) {
						insertDetailsStatement.setLong(1,user.getId());
						insertDetailsStatement.setString(2,user.getName());
						insertDetailsStatement.setString(3, user.getSurname());
						if(user.getBirthday() != null) {
							insertDetailsStatement.setDate(4, Date.valueOf(user.getBirthday()));
						} else {
							insertDetailsStatement.setNull(4,Types.DATE);
						}
						modifiedRows = insertDetailsStatement.executeUpdate();
						if (modifiedRows != 1) {
							throw new DaoException("error inserting user details data into the table");
						}
					}
				}

				connection.commit();

				return true;
			} catch (DaoException e) {
				connection.rollback();
				throw new DaoException(e);
			} finally {
				connection.setAutoCommit(true);
			}



		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		} finally {
			//sem.release();
			writeLock.unlock();
		}

	}





}
