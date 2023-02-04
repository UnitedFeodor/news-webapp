package by.htp.ex.controller.impl;

import java.io.IOException;

import by.htp.ex.controller.Command;
import by.htp.ex.constants.UserConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoSignOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		session.setAttribute(UserConstants.USER_ACTIVITY, "not active");
		session.setAttribute(UserConstants.USER_ROLE, UserConstants.ROLE_GUEST);
		response.sendRedirect("index.jsp");

	}

}
