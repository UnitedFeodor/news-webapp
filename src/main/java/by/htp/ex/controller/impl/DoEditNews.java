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


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        try {
            int id = Integer.parseInt(request.getParameter(NewsConstants.NEWS_ID));
            String newNewsDateStr = request.getParameter(NewsConstants.NEWS_DATE);
            SimpleDateFormat sdf = new SimpleDateFormat(NewsConstants.DATE_FORMAT);

            Date newNewsDate = new Date(sdf.parse(newNewsDateStr).getTime());

            News newNews = new News(
                    id,
                    request.getParameter(NewsConstants.NEWS_TITLE),
                    request.getParameter(NewsConstants.NEWS_BRIEF),
                    request.getParameter(NewsConstants.NEWS_CONTENT),
                    newNewsDate.toLocalDate());

            newsService.update(newNews);
            session.setAttribute(JSPConstants.JSP_SAVE_SUCCESS,JSPConstants.SUC);
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_NEWS_LIST);

        } catch (ParseException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"date parse error");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        } catch (ServiceException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"update error");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        }


    }
}
