package by.htp.ex.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.bean.UserInfo;
import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;

public class UserDAO implements IUserDAO	{

	List<UserInfo> userStorage = new ArrayList<UserInfo>();
	{
		userStorage.add(new UserInfo("user","password"));
		userStorage.add(new UserInfo("bob","ross"));

	}
	List<UserInfo> adminStorage = new ArrayList<UserInfo>();
	{
		adminStorage.add(new UserInfo("admin","password"));
		adminStorage.add(new UserInfo("ted","kaczynski"));

	}


	@Override
	public boolean logination(String login, String password) throws DaoException {

		UserInfo user = userStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null);
		UserInfo admin;
		if (user == null) {
			admin = adminStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null);
			if(admin == null || !admin.getPassword().equals(password)) {
				return false;
			} else {
				return true;
			}
		}

		if(!user.getPassword().equals(password)) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getRole(String login, String password) throws DaoException {


		if(adminStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null) != null) {
			return "admin";
		} else if(userStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null) != null) {
			return "user";
		} else {
			return "guest";
		}

	}

	@Override
	public boolean registration(UserInfo user) throws DaoException  {
		userStorage.add(user);
		return true;
	}

}
