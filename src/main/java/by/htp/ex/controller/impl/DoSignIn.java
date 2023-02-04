package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.constants.ViewConstants;
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

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login;
		String password;
		login = request.getParameter(JSP_LOGIN_PARAM);
		password = request.getParameter(JSP_PASSWORD_PARAM);
		HttpSession session = request.getSession(false);// TODO add user cabinet to change details and see them
		try {

			String role = service.signIn(login, password);

			if (!"guest".equals(role)) {
				session.setAttribute(UserConstants.USER_ACTIVITY, "active");
				session.setAttribute(UserConstants.USER_ROLE, role);
				response.sendRedirect("controller?command=go_to_news_list");
			} else {
				session.setAttribute(UserConstants.USER_ACTIVITY, "not active");
				session.setAttribute("auth_error", "wrong login or password");

				response.sendRedirect("controller?command=go_to_base_page");
			}

		} catch (ServiceException e) {

			session.setAttribute(ViewConstants.ERROR_MESSAGE, "sign in error");
			response.sendRedirect("controller?command=go_to_error_page");
		}



	}

}
