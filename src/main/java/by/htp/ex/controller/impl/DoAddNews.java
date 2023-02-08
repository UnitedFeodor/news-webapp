package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.constants.UserConstants;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DoAddNews implements Command {

    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        //int id = Integer.parseInt(request.getParameter(NewsConstants.NEWS_ID));

        String newNewsDateStr = request.getParameter(NewsConstants.NEWS_DATE);
        SimpleDateFormat sdf = new SimpleDateFormat(
                NewsConstants.DATE_FORMAT);
        Date newNewsDate = null;
        try {
            newNewsDate = new Date(sdf.parse(newNewsDateStr).getTime()); // working conversion
        } catch (ParseException e) {
            session.setAttribute(ViewConstants.ERROR_MESSAGE,"date parse error");
            response.sendRedirect("controller?command=go_to_error_page");
        }

        News newNews = new News();
        newNews.setTitle(request.getParameter(NewsConstants.NEWS_TITLE));
        newNews.setBrief(request.getParameter(NewsConstants.NEWS_BRIEF));
        newNews.setContent(request.getParameter(NewsConstants.NEWS_CONTENT));
        newNews.setNewsDate(newNewsDate);
        int userId = (int) session.getAttribute(UserConstants.USER_ID);
        newNews.setAuthorId(userId);

        try {
            newsService.add(newNews);
            session.setAttribute("save_success","suc");
            response.sendRedirect("controller?command=go_to_news_list");
        } catch (ServiceException e) {
            session.setAttribute(ViewConstants.ERROR_MESSAGE,"add error");
            response.sendRedirect("controller?command=go_to_error_page");

        }


    }
}
