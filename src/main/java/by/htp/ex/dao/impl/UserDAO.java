package by.htp.ex.dao.impl;

import java.sql.*;

import by.htp.ex.bean.User;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;
import by.htp.ex.dao.connection_pool.DBConstants;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements IUserDAO	{
	private static final int ROLE_ID_USER = 1;
	private static final int ROLE_ID_ADMIN = 2;
	private static final int ROLE_ID_GUEST = 3;

	private static final int STATUS_ID_INACTIVE = 1;
	private static final int STATUS_ID_ACTIVE = 2;
	private static final int STATUS_ID_BLOCKED = 3;

	private static final String Q_GET_ALL_USERS = "SELECT * FROM users";
	//private static final String Q_GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? AND password = ?";
	private static final String Q_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";

	private static final String Q_GET_ID_BY_LOGIN = "SELECT id FROM users WHERE login = ?";
	private static final String Q_GET_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
	private static final String Q_INSERT_USER = "INSERT INTO users (login,password,roles_id,status_id) VALUES (?,?,?,?)";

	private static final String Q_INSERT_USER_DETAILS = "INSERT INTO user_details (users_id,name,surname,birthday) VALUES (?,?,?,?)";
	@Override
	public boolean logination(String login, String password) throws DaoException {

		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement statement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				statement.setString(1,login);



				//statement.setString(2,password);
				try(ResultSet rs = statement.executeQuery()) {
					if (rs.isBeforeFirst()) {
						rs.next();
						String hashedPassword = rs.getString(UserConstants.DB_PASSWORD);
						return BCrypt.checkpw(password,hashedPassword);

					} else {
						return false; // no such login in db
					}
				} catch (IllegalArgumentException e) {
					return false;
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}


	}

	@Override
	public String getRole(String login, String password) throws DaoException {

		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				getUserStatement.setString(1,login);
				try(ResultSet rs = getUserStatement.executeQuery()) {
					User foundUser;
					if (!rs.isBeforeFirst() ) {
						throw new DaoException("no user with such login found"); // no such login found in db
					} else {
						rs.next();
						int role_id = rs.getInt(4);
						try(PreparedStatement getRoleStatement = connection.prepareStatement(Q_GET_ROLE_BY_ID);) {
							getRoleStatement.setInt(1,role_id);
							ResultSet rs2 = getRoleStatement.executeQuery();
							rs2.next();
							String dbRole = rs2.getString("title");
							return dbRole;

						}


					}
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public User getUserByLogin(String login) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				getUserStatement.setString(1,login);
				try(ResultSet rs = getUserStatement.executeQuery()) {
					User foundUser;
					if (!rs.isBeforeFirst() ) {
						throw new DaoException("no user with such login found"); // no such login found in db
					} else {
						rs.next();
						foundUser = new User(login,rs.getString(UserConstants.DB_PASSWORD));
						foundUser.setId(rs.getInt(UserConstants.DB_ID));
						String role = roleIdToRole(rs.getInt(UserConstants.DB_ROLE_ID)); // TODO ask: not very good to do this without db?
						foundUser.setRole(role);

						return foundUser;


					}
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public int getIdByLogin(String login) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				getUserStatement.setString(1,login);
				try(ResultSet rs = getUserStatement.executeQuery()) {
					User foundUser;
					if (!rs.isBeforeFirst() ) {
						throw new DaoException("no user with such login found"); // no such login found in db
					} else {
						rs.next();

						return rs.getInt(1);
					}
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	/**
	 *
	 *  user param is affected
	 */
	public boolean registration(User user) throws DaoException  {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				getUserStatement.setString(1,user.getEmail());
				try(ResultSet rs = getUserStatement.executeQuery()) {
					if (rs.isBeforeFirst() ) { // such login found in db
						return false;
					}
				}
			}
			try(PreparedStatement insertUserStatement = connection.prepareStatement(Q_INSERT_USER,Statement.RETURN_GENERATED_KEYS)) {
				insertUserStatement.setString(1,user.getEmail());

				String password = user.getPassword();
				String salt = BCrypt.gensalt();
				String hashedPassword = BCrypt.hashpw(password,salt);

				insertUserStatement.setString(2,hashedPassword);
				insertUserStatement.setInt(3,roleToRoleId(user.getRole()));
				insertUserStatement.setInt(4,STATUS_ID_INACTIVE);
				int modifiedRows = insertUserStatement.executeUpdate();
				if (modifiedRows != 1) {
					throw new SQLException("error inserting user data into the table");
				}

				try (ResultSet generatedKeys = insertUserStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						user.setId(generatedKeys.getInt(1));
					}
					else {
						throw new SQLException("creating user failed, no ID obtained");
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
							throw new SQLException("error inserting user details data into the table");
						}
					}
				}
				return true;
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}

	}

	private int roleToRoleId(String role) throws DaoException {
		switch (role) {
			case UserConstants.ROLE_USER -> {
				return ROLE_ID_USER;
			}
			case UserConstants.ROLE_ADMIN -> {
				return ROLE_ID_ADMIN;
			}
			case UserConstants.ROLE_GUEST -> {
				return ROLE_ID_GUEST;
			}
			default -> {
				throw new DaoException("role doesn't exist"); // maybe exception
			}

		}

	}

	private String roleIdToRole(int roleId) throws DaoException {
		switch (roleId) {
			case ROLE_ID_USER -> {
				return UserConstants.ROLE_USER;
			}
			case ROLE_ID_ADMIN -> {
				return UserConstants.ROLE_ADMIN;
			}
			case ROLE_ID_GUEST -> {
				return UserConstants.ROLE_GUEST;
			}
			default -> {
				throw new DaoException("role with such id doesn't exist"); // maybe exception
			}

		}

	}

}
