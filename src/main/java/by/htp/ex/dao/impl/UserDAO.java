package by.htp.ex.dao.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.bean.UserInfo;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;

public class UserDAO implements IUserDAO	{
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	public static final String ROLE_GUEST = "guest";

	List<UserInfo> userStorage = new ArrayList<>();
	{
		userStorage.add(new UserInfo("user","password"));
		userStorage.add(new UserInfo("bob","ross"));

	}
	List<UserInfo> adminStorage = new ArrayList<>();
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
			return admin != null && admin.getPassword().equals(password);
		}

		return user.getPassword().equals(password);
	}
	
	public String getRole(String login, String password) throws DaoException {


		if(adminStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null) != null) {
			return ROLE_ADMIN;
		} else if(userStorage.stream().filter(o -> o.getEmail().equals(login)).findAny().orElse(null) != null) {
			return ROLE_USER;
		} else {
			return ROLE_GUEST;
		}

	}

	@Override
	public boolean registration(UserInfo user) throws DaoException  {
		if(userStorage.stream().filter(o -> o.getEmail().equals(user.getEmail())).findAny().orElse(null) != null) {
			return false;
		} else {
			userStorage.add(user);
			return true;
		}
	}

}
