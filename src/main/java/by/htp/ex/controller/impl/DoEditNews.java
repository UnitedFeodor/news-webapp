package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DoEditNews implements Command {
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    private static final String JSP_SAVE_SUCCESS = "save_success";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int id = Integer.parseInt(request.getParameter(NewsConstants.NEWS_ID));
        String newNewsDateStr = request.getParameter(NewsConstants.NEWS_DATE);
        SimpleDateFormat sdf = new SimpleDateFormat(NewsConstants.DATE_FORMAT);
        Date newNewsDate = null;
        try {
            newNewsDate = new Date(sdf.parse(newNewsDateStr).getTime());
        } catch (ParseException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"date parse error");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        }
        News newNews = new News(
                id,
                request.getParameter(NewsConstants.NEWS_TITLE),
                request.getParameter(NewsConstants.NEWS_BRIEF),
                request.getParameter(NewsConstants.NEWS_CONTENT),
                newNewsDate);
        try {
            newsService.update(newNews);
            session.setAttribute(JSP_SAVE_SUCCESS,JSPConstants.SUC);
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_NEWS_LIST);
        } catch (ServiceException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"update error");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        }


    }
}
