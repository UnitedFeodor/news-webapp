package by.htp.ex.service;

import by.htp.ex.bean.User;

public interface IUserService {
	
	String signIn(String login, String password) throws ServiceException;
	boolean register(User user) throws ServiceException;

}
