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

public class UserDAO implements IUserDAO	{
	/*
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	public static final String ROLE_GUEST = "guest";
*/
	//private ConnectionPool connectionPool = new ConnectionPool();

	/*
	List<User> userStorage = new ArrayList<>();
	{
		userStorage.add(new User("s","s2", UserConstants.ROLE_USER));
		userStorage.add(new User("ss","ross", UserConstants.ROLE_USER));

	}
	List<User> adminStorage = new ArrayList<>();
	{
		adminStorage.add(new User("sss","s3",UserConstants.ROLE_ADMIN));
		adminStorage.add(new User("ted","kaczynski",UserConstants.ROLE_ADMIN));

	}*/


	private static final int ROLE_ID_USER = 1;
	private static final int ROLE_ID_ADMIN = 2;
	private static final int ROLE_ID_GUEST = 3;

	private static final int STATUS_ID_INACTIVE = 1;
	private static final int STATUS_ID_ACTIVE = 2;
	private static final int STATUS_ID_BLOCKED = 3;

	private static final String Q_GET_ALL_USERS = "SELECT * FROM users";
	private static final String Q_GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? AND password = ?";
	private static final String Q_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
	private static final String Q_GET_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
	private static final String Q_INSERT_USER = "INSERT INTO users (login,password,roles_id,status_id) VALUES (?,?,?,?)";

	private static final String Q_INSERT_USER_DETAILS = "INSERT INTO user_details (users_id,name,surname,birthday) VALUES (?,?,?,?)";
	@Override
	public boolean logination(String login, String password) throws DaoException {
		/*
		try {
			Class.forName(DBConstants.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new DaoException(e);
		}
		try(Connection connection = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.DB_USER,
				DBConstants.DB_PASSWORD)) {
		*/
		//try(Connection connection = connectionPool.takeConnection()) {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement statement = connection.prepareStatement(Q_GET_USER_BY_LOGIN_AND_PASSWORD)) {
				statement.setString(1,login);
				statement.setString(2,password);
				try(ResultSet rs = statement.executeQuery()) {
					return rs.isBeforeFirst();
					/*
					if (!rs.isBeforeFirst() ) {
						return false; // no such login found in db
					} else {

						return true;
					}*/
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}

		/*
		User user = userStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null);
		User admin;
		if (user == null) {
			admin = adminStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null);
			return admin != null && admin.getPassword().equals(password);
		}

		return user.getPassword().equals(password);
	*/
	}

	
	public String getRole(String login, String password) throws DaoException {
		/*
		try {
			Class.forName(DBConstants.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new DaoException(e);
		}
		try(Connection connection = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.DB_USER,
				DBConstants.DB_PASSWORD)) {
		*/
		//try(Connection connection = connectionPool.takeConnection()) {
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

/*
		if(adminStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null) != null) {
			return UserConstants.ROLE_ADMIN;
		} else if(userStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null) != null) {
			return UserConstants.ROLE_USER;
		} else {
			return UserConstants.ROLE_GUEST;
		}
*/
	}

	@Override
	public boolean registration(User user) throws DaoException  {
		/*
		try {
			Class.forName(DBConstants.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new DaoException(e);
		}
		try(Connection connection = DriverManager.getConnection(DBConstants.DB_URL, DBConstants.DB_USER,
				DBConstants.DB_PASSWORD)) {
		*/	//try(Connection connection = connectionPool.takeConnection()) {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection()) {
			try(PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_LOGIN)) {
				getUserStatement.setString(1,user.getEmail());
				try(ResultSet rs = getUserStatement.executeQuery()) {


					if (!rs.isBeforeFirst() ) { // no such login found in db
						try(PreparedStatement insertUserStatement = connection.prepareStatement(Q_INSERT_USER,Statement.RETURN_GENERATED_KEYS)) {
							insertUserStatement.setString(1,user.getEmail());
							insertUserStatement.setString(2,user.getPassword());
							insertUserStatement.setInt(3,roleToRoleId(user.getRole()));
							insertUserStatement.setInt(4,STATUS_ID_INACTIVE);
							int modifiedRows = insertUserStatement.executeUpdate();
							if (modifiedRows != 1) {
								throw new SQLException("error inserting user data into the table");
							} else {
								try (ResultSet generatedKeys = insertUserStatement.getGeneratedKeys()) {
									if (generatedKeys.next()) {
										user.setId(generatedKeys.getLong(1));
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
						}

					} else {
						return false; // such login found in db
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
		/*
		if(userStorage.stream().filter(o -> o.getEmail().equals(user.getEmail())).findAny().orElse(null) != null) {
			return false;
		} else {
			userStorage.add(user);
			return true;
		}*/
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

}
