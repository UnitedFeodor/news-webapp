package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.controller.constants.NewsConstants;
import by.htp.ex.controller.constants.ViewConstants;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;



public class GoToEditNews implements Command {

    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        News news;
        String id;

        id = request.getParameter(NewsConstants.NEWS_ID);

        try {
            news = newsService.findById(Integer.parseInt(id));
            request.setAttribute(ViewConstants.NEWS, news);
            request.setAttribute(ViewConstants.PRESENTATION, ViewConstants.EDIT_NEWS);

            request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
        } catch (ServiceException e) {
            HttpSession session = request.getSession(false);
            session.setAttribute(ViewConstants.ERROR_MESSAGE, "cannot find the news by id");
            response.sendRedirect("controller?command=go_to_error_page");
        }

    }
}

