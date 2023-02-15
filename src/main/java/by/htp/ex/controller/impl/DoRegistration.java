package by.htp.ex.controller.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import by.htp.ex.bean.User;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.JSPConstants;
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

	private static final String JSP_NAME_PARAM = "name";
	private static final String JSP_SURNAME_PARAM = "surname";
	private static final String JSP_BIRTHDAY_PARAM = "birthday";

	private static final String JSP_ROLE_PARAM = "roles";

	private static final String JSP_REGISTER_ERROR = "register_error";
	private static final String JSP_REGISTER_SUCCESS = "register_success";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {
			User newUser = new User();
			newUser.setEmail(request.getParameter(JSP_LOGIN_PARAM));
			newUser.setPassword(request.getParameter(JSP_PASSWORD_PARAM));
			newUser.setRole(request.getParameter(JSP_ROLE_PARAM));

			String name = request.getParameter(JSP_NAME_PARAM) != null ? request.getParameter(JSP_NAME_PARAM) : "";
			String surname = request.getParameter(JSP_SURNAME_PARAM) != null ? request.getParameter(JSP_SURNAME_PARAM) : "";
			newUser.setName(name);
			newUser.setSurname(surname);
			String birthday = request.getParameter(JSP_BIRTHDAY_PARAM);

			// if the user provided input for birthday then parse and save it
			if(birthday != null && !birthday.equals("")) {
				newUser.setBirthday(LocalDate.parse(birthday, DateTimeFormatter.ofPattern(UserConstants.DATE_FORMAT)));
			}

			if (!service.register(newUser)) {
				session.setAttribute(JSP_REGISTER_ERROR, JSPConstants.ERR);
			} else {
				session.setAttribute(JSP_REGISTER_SUCCESS, JSPConstants.SUC);
			}

			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_BASE_PAGE);
		} catch (ServiceException e) {
			session.setAttribute(JSPConstants.ERROR_MESSAGE,"register error");
			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
		}

	}

}
