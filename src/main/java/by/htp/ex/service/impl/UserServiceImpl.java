package by.htp.ex.service.impl;

import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.IUserService;
import by.htp.ex.util.validation.IUserValidator;
import by.htp.ex.util.validation.ValidatorProvider;

public class UserServiceImpl implements IUserService{

	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	public static final String ROLE_GUEST = "guest";

	private final IUserValidator userValidator = ValidatorProvider.getInstance().getUserValidator();
	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();

	@Override
	public User signIn(String login, String password) throws ServiceException {
		
		/*
		 * if(!userDataValidation.checkAUthData(login, password)) { throw new
		 * ServiceException("login ...... "); }
		 */
		User userToValidate = new User();
		userToValidate.setEmail(login);
		userToValidate.setPassword(password);
		if (!userValidator.setUser(userToValidate).checkLogin().checkPassword().isValid()) {
			return null;
		}

		try {
			if(userDAO.signIn(login, password)) {
				return userDAO.getUserByLogin(login);
			} else {
				return null;
			}
			
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public int getId(String login) throws ServiceException {
		User userToValidate = new User();
		userToValidate.setEmail(login);
		if (!userValidator.setUser(userToValidate).checkLogin().isValid()) {
			throw new ServiceException(userValidator.getErrors().toString());
		}

		try {
			return userDAO.getIdByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean register(User user)  throws ServiceException {
		User userToValidate = user;
		if (!userValidator.setUser(userToValidate).checkAllExceptId().isValid()) {
			return false;
			//throw new ServiceException(userValidator.getAllErrors().toString());
		}
		try {
			return userDAO.registration(user);

		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

}
