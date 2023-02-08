package by.htp.ex.controller.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import by.htp.ex.bean.User;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.ViewConstants;
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

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {
			User newUser = new User();
			newUser.setEmail(request.getParameter(JSP_LOGIN_PARAM));
			newUser.setPassword(request.getParameter(JSP_PASSWORD_PARAM));

			String name = request.getParameter(JSP_NAME_PARAM) != null ? request.getParameter(JSP_NAME_PARAM) : "";
			String surname = request.getParameter(JSP_SURNAME_PARAM) != null ? request.getParameter(JSP_SURNAME_PARAM) : "";
			newUser.setName(name);
			newUser.setSurname(surname);
			String birthday = request.getParameter(JSP_BIRTHDAY_PARAM);
			if(birthday != null && !birthday.equals("")) {
				newUser.setBirthday(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			}
			if (!service.register(newUser)) {

				session.setAttribute("register_error","err");
			} else {

				//session.setAttribute(UserConstants.USER_ID,service.getId(request.getParameter(JSP_LOGIN_PARAM)));
				session.setAttribute("register_success", "suc");
			}
			response.sendRedirect("controller?command=go_to_base_page");
		} catch (ServiceException e) {
			session.setAttribute(ViewConstants.ERROR_MESSAGE,"register error");
			response.sendRedirect("controller?command=go_to_error_page");
		}

	}

}
