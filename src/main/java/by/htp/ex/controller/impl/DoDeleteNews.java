package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.controller.CommandName;
import by.htp.ex.controller.impl.utilities.ControllerSecurity;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.htp.ex.controller.constants.NewsConstants.NEWS_ID;
import static by.htp.ex.controller.constants.UserConstants.USER_ROLE;
import static by.htp.ex.controller.constants.ViewConstants.ERROR_MESSAGE;

public class DoDeleteNews implements Command {
    //TODO add success  message
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        //String role = (String) session.getAttribute(USER_ROLE);

        if (ControllerSecurity.canExecuteThisRequest((String) session.getAttribute(USER_ROLE), CommandName.DO_DELETE_NEWS)) {
            String[] newsIds = request.getParameterValues(NEWS_ID);
            if (newsIds != null) {
                try {
                    newsService.delete(newsIds);
                } catch (ServiceException e) {
                    session.setAttribute(ERROR_MESSAGE,"delete error");
                    response.sendRedirect("controller?command=go_to_error_page");
                }
                response.sendRedirect("controller?command=go_to_news_list");
            } else {
                session.setAttribute(ERROR_MESSAGE,"no news to delete selected");
                response.sendRedirect("controller?command=go_to_error_page");
                //request.getRequestDispatcher("/WEB-INF/pages/tiles/error.jsp").forward(request, response);

                //response.sendRedirect("/WEB-INF/pages/tiles/error.jsp");
            }
        } else {
            session.setAttribute(ERROR_MESSAGE,"user with such role cannot delete news");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
