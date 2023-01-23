package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.UserInfo;
import by.htp.ex.controller.Command;
import by.htp.ex.controller.constants.ViewConstants;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public class DoRegistration implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {
			UserInfo newUser = new UserInfo();
			newUser.setEmail(request.getParameter(JSP_LOGIN_PARAM));
			newUser.setPassword(request.getParameter(JSP_PASSWORD_PARAM));
			if (!service.register(newUser)) {

				session.setAttribute("register_error","err");
			} else {

				session.setAttribute("register_success", "suc");
			}
			response.sendRedirect("controller?command=go_to_base_page");
		} catch (ServiceException e) {
			session.setAttribute(ViewConstants.ERROR_MESSAGE,"register error");
			response.sendRedirect("controller?command=go_to_error_page");
		}

	}

}
