package by.htp.ex.dao;

import by.htp.ex.bean.User;

public interface IUserDAO {
	
	boolean logination(String login, String password) throws DaoException;
	boolean registration(User user) throws DaoException;
	String getRole(String login, String password) throws DaoException;

	int getIdByLogin(String login) throws DaoException;
	User getUserByLogin(String login) throws DaoException;

}
