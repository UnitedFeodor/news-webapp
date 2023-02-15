package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.constants.UserConstants;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DoAddNews implements Command {

    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    private static final String JSP_SAVE_SUCCESS = "save_success";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        //int id = Integer.parseInt(request.getParameter(NewsConstants.NEWS_ID));

        String newNewsDateStr = request.getParameter(NewsConstants.NEWS_DATE);
        try {
            LocalDate newNewsDate = LocalDate.parse(newNewsDateStr, DateTimeFormatter.ofPattern(UserConstants.DATE_FORMAT)); // working conversion

            News newNews = new News();
            newNews.setTitle(request.getParameter(NewsConstants.NEWS_TITLE));
            newNews.setBrief(request.getParameter(NewsConstants.NEWS_BRIEF));
            newNews.setContent(request.getParameter(NewsConstants.NEWS_CONTENT));
            newNews.setNewsDate(newNewsDate);
            int userId = (int) session.getAttribute(UserConstants.USER_ID);
            newNews.setAuthorId(userId);

            // TODO check all try catches
            newsService.add(newNews);
            session.setAttribute(JSP_SAVE_SUCCESS, JSPConstants.SUC);
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_NEWS_LIST);


        } catch (DateTimeParseException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE, "date parse error");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        } catch (ServiceException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE, "add error");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);

        }
        }


}
