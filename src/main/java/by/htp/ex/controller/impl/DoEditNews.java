package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.NewsConstants;
import by.htp.ex.constants.ViewConstants;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;




public class DoEditNews implements Command {
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int id = Integer.parseInt(request.getParameter(NewsConstants.NEWS_ID));

        News newNews = new News(id,request.getParameter(NewsConstants.NEWS_TITLE),request.getParameter(NewsConstants.NEWS_BRIEF),request.getParameter(NewsConstants.NEWS_CONTENT),request.getParameter(NewsConstants.NEWS_DATE));
        try {
            newsService.update(newNews);
            session.setAttribute("save_success","suc");
            response.sendRedirect("controller?command=go_to_news_list");
        } catch (ServiceException e) {
            session.setAttribute(ViewConstants.ERROR_MESSAGE,"update error");
            response.sendRedirect("controller?command=go_to_error_page");
        }


    }
}
