package by.htp.ex.service.impl;

import by.htp.ex.bean.User;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.IUserService;

public class UserServiceImpl implements IUserService{

	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	public static final String ROLE_GUEST = "guest";

	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();
//	private final UserDataValidation userDataValidation = ValidationProvider.getIntsance().getUserDataVelidation();
	
	@Override
	public User signIn(String login, String password) throws ServiceException {
		
		/*
		 * if(!userDataValidation.checkAUthData(login, password)) { throw new
		 * ServiceException("login ...... "); }
		 */
		
		try {
			if(userDAO.logination(login, password)) {
				return userDAO.getUserByLogin(login);
			} else {
				return null; // TODO may be a bad idea
			}
			
		} catch(DaoException e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public int getId(String login) throws ServiceException {
		try {
			return userDAO.getIdByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean register(User user)  throws ServiceException {
		try {
			return userDAO.registration(user);

		} catch(DaoException e) {
			throw new ServiceException(e);
		}
	}

}
