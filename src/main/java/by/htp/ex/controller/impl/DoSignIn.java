package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.User;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.constants.JSPConstants;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignIn implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";

	private static final String JSP_AUTHENTICATION_ERROR = "auth_error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);// TODO add user cabinet to change details and see them
		try {
			String login;
			String password;
			login = request.getParameter(JSP_LOGIN_PARAM);
			password = request.getParameter(JSP_PASSWORD_PARAM);

			User user = service.signIn(login, password);

			if (user != null) {
				session.setAttribute(UserConstants.USER_ACTIVITY, UserConstants.USER_STATUS_ACTIVE);
				session.setAttribute(UserConstants.USER_ROLE, user.getRole());
				session.setAttribute(UserConstants.USER_ID,user.getId());
				response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_NEWS_LIST);
			} else {
				session.setAttribute(UserConstants.USER_ACTIVITY, UserConstants.USER_STATUS_NOT_ACTIVE);
				session.setAttribute(JSP_AUTHENTICATION_ERROR, "wrong login or password");

				response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_BASE_PAGE);
			}

		} catch (ServiceException e) {

			session.setAttribute(JSPConstants.ERROR_MESSAGE, "sign in error");
			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
		}



	}

}
