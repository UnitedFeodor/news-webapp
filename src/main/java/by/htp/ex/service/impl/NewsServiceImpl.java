package by.htp.ex.service.impl;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.INewsValidator;
import by.htp.ex.util.validation.ValidatorProvider;

public class NewsServiceImpl implements INewsService{

	private final INewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();
	private final INewsValidator newsValidator = ValidatorProvider.getInstance().getNewsValidator();

	@Override
	public void delete(int[] newsIds) throws ServiceException {
		News news = new News();
		for (int currId: newsIds) {
			news.setIdNews(currId);
			if(!newsValidator.setNews(news).checkId().isValid()) {
				throw new ServiceException(newsValidator.getErrors().toString());
			}
		}
		try {
			newsDAO.deleteNews(newsIds);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void add(News news) throws ServiceException {
		if(!newsValidator.setNews(news).checkAllExceptId().isValid()) {
			throw new ServiceException(newsValidator.getErrors().toString());
		}

		try {
			newsDAO.addNews(news);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public int getTotalNewsAmount() throws ServiceException {
		try {
			return newsDAO.getTotalNewsAmount();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void find()  throws ServiceException{
		// TODO Auto-generated method stub
	}

	@Override
	public void update(News news)  throws ServiceException{
		if(!newsValidator.setNews(news).checkId().checkTitle().checkDate().checkBrief().checkContent().isValid()) {
			throw new ServiceException(newsValidator.getErrors().toString());
		} // TODO change the way author id is handled

		try {
			newsDAO.updateNews(news);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<News> getCountNewsStartingFrom(int count, int from) throws ServiceException {
		if (count <= 0) {
			throw new ServiceException("invalid number of news requested");
		}
		if (from < 0) {
			throw new ServiceException("invalid page requested");
		}

		try {
			return newsDAO.getCountNewsStartingFrom(count, from);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {
		try {
			return newsDAO.getList();
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public News findById(int id) throws ServiceException {
		News news = new News();
		news.setIdNews(id);
		if(!newsValidator.setNews(news).checkId().isValid()) {
			throw new ServiceException(newsValidator.getErrors().toString());
		}
		try {
			return newsDAO.fetchById(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
