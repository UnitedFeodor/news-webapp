package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToErrorPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("error_msg") == null) {

            request.setAttribute("error_msg","no such command error");
        }
        request.getRequestDispatcher("/WEB-INF/pages/tiles/error.jsp").forward(request, response);
    }
}
