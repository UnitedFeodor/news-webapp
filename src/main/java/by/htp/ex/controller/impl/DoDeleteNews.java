package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.constants.NewsConstants;
import by.htp.ex.constants.JSPConstants;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;



public class DoDeleteNews implements Command {

    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    private static final String JSP_DELETE_SUCCESS = "delete_success";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String[] newsIds = request.getParameterValues(NewsConstants.NEWS_ID);
        if (newsIds != null) {
            try {
                newsService.delete(newsIds);
                session.setAttribute(JSP_DELETE_SUCCESS,JSPConstants.SUC);
                response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_NEWS_LIST);
            } catch (ServiceException e) {
                session.setAttribute(JSPConstants.ERROR_MESSAGE,"delete error");
                response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
            }

        } else {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"no news to delete selected");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);

        }

    }
}
