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
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
	// TODO watch Java_19 Вебинар_08.02.2023 for pagination ideas
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> newsList;
		try {
			newsList = newsService.list();
			if (newsList.size() > 0) {
				request.setAttribute(JSPConstants.NEWS, newsList);
			}
			request.setAttribute(JSPConstants.PRESENTATION, JSPConstants.NEWS_LIST);

			request.getRequestDispatcher(JSPConstants.BASE_LAYOUT_JSP_URI).forward(request, response);

		} catch (ServiceException e) {
			HttpSession session = request.getSession(false);
			session.setAttribute(JSPConstants.ERROR_MESSAGE,"cannot get the list of news");
			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
		}
		
	}

}
