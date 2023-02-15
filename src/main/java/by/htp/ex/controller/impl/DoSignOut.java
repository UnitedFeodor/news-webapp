package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.constants.JSPConstants;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.service.IUserService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		session.setAttribute(UserConstants.USER_ACTIVITY, UserConstants.USER_STATUS_NOT_ACTIVE);
		session.setAttribute(UserConstants.USER_ROLE, UserConstants.ROLE_GUEST);
		session.removeAttribute(UserConstants.USER_ID);
		response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_BASE_PAGE);


	}

}
