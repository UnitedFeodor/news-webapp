package by.htp.ex.controller.impl;

import by.htp.ex.constants.JSPConstants;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
