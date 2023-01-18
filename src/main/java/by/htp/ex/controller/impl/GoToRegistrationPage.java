package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.controller.CommandName;
import by.htp.ex.controller.impl.utilities.ControllerSecurity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.htp.ex.controller.constants.UserConstants.USER_ROLE;
import static by.htp.ex.controller.constants.ViewConstants.ERROR_MESSAGE;

public class GoToRegistrationPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (ControllerSecurity.canExecuteThisRequest((String) session.getAttribute(USER_ROLE), CommandName.GO_TO_REGISTRATION_PAGE)) {
			request.setAttribute("presentation", "registration");
			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
		}  else {
			session.setAttribute(ERROR_MESSAGE,"user with such role cannot register");
			response.sendRedirect("controller?command=go_to_error_page");
		}
	}

}
