package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.controller.constants.ViewConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class GoToErrorPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute(ViewConstants.ERROR_MESSAGE);

        if (errorMessage == null) {

            session.setAttribute(ViewConstants.ERROR_MESSAGE,"no such command error");
        }
        request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);

    }
}
