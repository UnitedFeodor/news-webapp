package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.constants.JSPConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class GoToErrorPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute(JSPConstants.ERROR_MESSAGE);

        if (errorMessage == null) {

            if (request.getPathInfo().contains(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE)) {
                session.setAttribute(JSPConstants.ERROR_MESSAGE,"jsp loading error");
            } else {
                session.setAttribute(JSPConstants.ERROR_MESSAGE,"no such command error");
            }
        }
        request.getRequestDispatcher(JSPConstants.BASE_LAYOUT_JSP_URI).forward(request, response);

    }
}
