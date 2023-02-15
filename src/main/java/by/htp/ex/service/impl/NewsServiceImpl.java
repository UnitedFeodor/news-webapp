package by.htp.ex.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.constants.NewsConstants;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;

public class NewsServiceImpl implements INewsService{

	private final INewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();

	@Override
	public void delete(String[] newsIds) throws ServiceException {
		try {
			newsDAO.deleteNews(newsIds);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void add(News news) throws ServiceException {
		try {
			newsDAO.addNews(news); // TODO make a date check method in validation
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
		try {
			newsDAO.updateNews(news);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<News> latestList(int count) throws ServiceException {
		try {
			return newsDAO.getLatestList(count);
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
		try {
			return newsDAO.fetchById(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
