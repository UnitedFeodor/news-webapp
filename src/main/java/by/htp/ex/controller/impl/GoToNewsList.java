package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.JSPConstants;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public class GoToNewsList implements Command {

	public static final String JSP_COUNT_PARAM = "count";
	public static final String JSP_PAGE_NUMBER_PARAM = "page";
	public static final String JSP_FINAL_PAGE_NUMBER = "final_page_number";
	private final int DEFAULT_COUNT = 5;
	private final int DEFAULT_PAGE = 1;
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> newsList;
		try {

			int newsPage;
			String pageParam = request.getParameter(JSP_PAGE_NUMBER_PARAM);
			if (pageParam == null) {
				newsPage = DEFAULT_PAGE;
			} else {
				newsPage = Integer.parseInt(pageParam);
			}

			String sessionCountParam = (String) request.getSession(false).getAttribute(JSP_COUNT_PARAM);
			String countParam = request.getParameter(JSP_COUNT_PARAM);
			int newsCount;
			if (countParam == null) {

				if (sessionCountParam == null) {
					newsCount = DEFAULT_COUNT;
				} else {
					newsCount = Integer.parseInt(sessionCountParam);
				}

			} else {
				newsCount = Integer.parseInt(countParam);
			}

			if (countParam != null && sessionCountParam != null && !countParam.equals(sessionCountParam)) {
				newsPage = DEFAULT_PAGE;
			}



			newsList = newsService.getCountNewsStartingFrom(newsCount,newsPage);
			if (newsList.size() > 0) {
				request.setAttribute(JSPConstants.NEWS, newsList);
			}
			int totalNewsAmount = newsService.getTotalNewsAmount();
			int finalPageNumber = totalNewsAmount % newsCount == 0 ? totalNewsAmount / newsCount : totalNewsAmount / newsCount + 1;

			request.setAttribute(JSP_FINAL_PAGE_NUMBER,finalPageNumber);
			request.setAttribute(JSP_PAGE_NUMBER_PARAM,newsPage);
			request.getSession(false).setAttribute(JSP_COUNT_PARAM,String.valueOf(newsCount));

			request.setAttribute(JSPConstants.PRESENTATION, JSPConstants.NEWS_LIST);

			request.getRequestDispatcher(JSPConstants.BASE_LAYOUT_JSP_URI).forward(request, response);


		} catch (ServiceException e) {
			HttpSession session = request.getSession(false);
			session.setAttribute(JSPConstants.ERROR_MESSAGE,"cannot get the list of news");
			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
		} catch (NumberFormatException e) {
			HttpSession session = request.getSession(false);
			session.setAttribute(JSPConstants.ERROR_MESSAGE,"invalid request parameters");
			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
		}
		
	}

}
