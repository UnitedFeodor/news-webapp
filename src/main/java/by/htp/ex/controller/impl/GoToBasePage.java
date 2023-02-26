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



public class GoToBasePage implements Command{
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> latestNews;
		try {
			latestNews = newsService.list();
			if(latestNews.size() > 0) {
				request.setAttribute(JSPConstants.NEWS, latestNews);
			}

			request.getRequestDispatcher(JSPConstants.BASE_LAYOUT_JSP_URI).forward(request, response);
		} catch (ServiceException e) {
			HttpSession session = request.getSession(false);
			session.setAttribute(JSPConstants.ERROR_MESSAGE,"cannot get the latest list of news");
			response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
		}

		
	}

}
