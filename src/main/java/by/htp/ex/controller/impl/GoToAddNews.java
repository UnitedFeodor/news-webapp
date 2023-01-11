package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.controller.impl.utilities.ControllerSecurity;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.htp.ex.controller.constants.ViewAttributes.ERROR_MESSAGE;
import static by.htp.ex.controller.constants.ViewAttributes.PRESENTATION;

public class GoToAddNews implements Command {
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        //String role = (String) session.getAttribute(USER_ROLE);

        if (ControllerSecurity.canExecuteThisRequest(session)) {

            int id;
            try {
                id = newsService.list().size() + 1;
                News news = new News(id, "", "", "", "");
                request.setAttribute("news", news);
                request.setAttribute(PRESENTATION, "addNews");
                request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
            } catch (ServiceException e) {

                session.setAttribute(ERROR_MESSAGE,"cannot get the list of news");
                response.sendRedirect("controller?command=go_to_error_page");
            }

        } else {
            session.setAttribute(ERROR_MESSAGE,"user with such role cannot add news");
            response.sendRedirect("controller?command=go_to_error_page");
        }

    }
}
