package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.htp.ex.bean.ViewAttributes.ERROR_MESSAGE;


public class GoToErrorPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute(ERROR_MESSAGE) == null) {

            request.setAttribute(ERROR_MESSAGE,"no such command error");
        }
        request.getRequestDispatcher("/WEB-INF/pages/tiles/error.jsp").forward(request, response);
    }
}
