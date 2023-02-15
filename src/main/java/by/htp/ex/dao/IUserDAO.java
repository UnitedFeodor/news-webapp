package by.htp.ex.dao;

import by.htp.ex.bean.User;

public interface IUserDAO {
	
	boolean signIn(String login, String password) throws DaoException;

	//boolean signOut(int id) throws DaoException;
	boolean registration(User user) throws DaoException;
	String getRole(String login, String password) throws DaoException;

	int getIdByLogin(String login) throws DaoException;
	User getUserByLogin(String login) throws DaoException;


}
