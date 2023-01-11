package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import by.htp.ex.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.htp.ex.controller.constants.UserAttributes.USER_ROLE;
import static by.htp.ex.controller.constants.ViewAttributes.ERROR_MESSAGE;

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

		// small validation

		try {

			String role = service.signIn(login, password);

			if (!"guest".equals(role)) {
				request.getSession(true).setAttribute("user", "active");
				request.getSession().setAttribute(USER_ROLE, role);
				response.sendRedirect("controller?command=go_to_news_list");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", "not active");
				session.setAttribute("AuthenticationError", "wrong login or password");

				response.sendRedirect("controller?command=go_to_base_page");
				//request.getRequestDispatcher("/WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
			}
			
		} catch (ServiceException e) {

			request.getSession(true).setAttribute(ERROR_MESSAGE,"sign in error");
			response.sendRedirect("controller?command=go_to_error_page");
		}



	}

}
