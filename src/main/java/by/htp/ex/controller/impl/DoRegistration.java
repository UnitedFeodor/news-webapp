package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.controller.Command;
import by.htp.ex.controller.CommandName;
import by.htp.ex.controller.impl.utilities.ControllerSecurity;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.htp.ex.controller.constants.UserConstants.USER_ROLE;
import static by.htp.ex.controller.constants.ViewConstants.ERROR_MESSAGE;

public class DoRegistration implements Command {

	private final IUserService service = ServiceProvider.getInstance().getUserService();

	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Service param check everywhere in impl
		//	TODO userdao like newsdao fake implementation
		// TODO better error handling with filters etc.
		// TODO success message for registration result

		HttpSession session = request.getSession(false);

		if (ControllerSecurity.canExecuteThisRequest((String) session.getAttribute(USER_ROLE), CommandName.DO_REGISTRATION)) {
			try {
				NewUserInfo newUser = new NewUserInfo();
				newUser.setEmail(request.getParameter(JSP_LOGIN_PARAM));
				newUser.setPassword(request.getParameter(JSP_PASSWORD_PARAM));
				service.register(newUser);
				response.sendRedirect("controller?command=go_to_base_page");
			} catch (ServiceException e) {
				session.setAttribute(ERROR_MESSAGE,"register error");
				response.sendRedirect("controller?command=go_to_error_page");
			}

		}  else {
			session.setAttribute(ERROR_MESSAGE,"user with such role cannot register");
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}

}
