package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.JSPConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class GoToAddNews implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            News news = new News();
            request.setAttribute(JSPConstants.NEWS, news);
            request.setAttribute(JSPConstants.PRESENTATION, JSPConstants.ADD_NEWS);

            request.getRequestDispatcher(JSPConstants.BASE_LAYOUT_JSP_URI).forward(request, response);

        } catch (Exception e) {
            HttpSession session = request.getSession(false);
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"cannot get the add news page");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        }


    }
}
