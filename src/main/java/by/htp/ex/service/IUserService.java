package by.htp.ex.service;

import by.htp.ex.bean.UserInfo;

public interface IUserService {
	
	String signIn(String login, String password) throws ServiceException;
	boolean register(UserInfo user) throws ServiceException;

}
