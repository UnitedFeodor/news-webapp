package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.htp.ex.controller.constants.ViewConstants.ERROR_MESSAGE;


public class GoToErrorPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String errorMessage = (String) session.getAttribute(ERROR_MESSAGE);

        if (errorMessage == null) {

            session.setAttribute(ERROR_MESSAGE,"no such command error");
        }
        request.getRequestDispatcher("WEB-INF/pages/tiles/error.jsp").forward(request, response);
        //response.sendRedirect("/WEB-INF/pages/tiles/error.jsp");
    }
}
