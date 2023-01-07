package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DoEditNews implements Command {


    private final String NEWS_TITLE = "news_title";
    private final String NEWS_DATE = "news_date";
    private final String NEWS_BRIEF = "news_brief";
    private final String NEWS_CONTENT = "news_content";



    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        News newNews = new News(id,request.getParameter(NEWS_TITLE),request.getParameter(NEWS_BRIEF),request.getParameter(NEWS_CONTENT),request.getParameter(NEWS_DATE));
        newsService.update(newNews);
        response.sendRedirect("controller?command=go_to_news_list");
    }
}
