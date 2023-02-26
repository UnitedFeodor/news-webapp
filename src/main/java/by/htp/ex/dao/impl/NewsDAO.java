package by.htp.ex.dao.impl;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;

public class NewsDAO implements INewsDAO {

	private static final String DB_ID = "id";
	private static final String DB_TITLE = "title";
	private static final String DB_DATE = "date_added";
	private static final String DB_BRIEF = "brief";
	private static final String DB_CONTENT = "content";
	
	
	private static final String Q_INSERT_NEWS = "INSERT INTO news (title, date_added, brief, content, authors_user_id, news_status_id) VALUES (?,?,?,?,?,?)";

	private static final String Q_DELETE_IDS = "DELETE FROM news WHERE id = ?";
	private static final String Q_UPDATE_NEWS_BY_ID = "UPDATE news SET title = ?, brief = ?, content = ?,date_added = ? WHERE id = ?";
	private static final String Q_GET_ALL_NEWS = "SELECT * FROM news";
	private static final String Q_GET_COUNT_OF_ALL_NEWS = "SELECT COUNT(*) FROM news";
	private static final String Q_GET_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
	private static final String Q_GET_ALL_NEWS_BY_DATE_DESC = "SELECT * FROM news ORDER BY date_added DESC";

	private static final String Q_GET_ALL_NEWS_BY_DATE_DESC_LIMIT = "SELECT * FROM news ORDER BY date_added DESC LIMIT ?";

	private static final String Q_GET_COUNT_NEWS_AFTER_DATE_DESC = "SELECT * FROM news WHERE date_added > ? ORDER BY date_added LIMIT ?";

	private static final String Q_GET_COUNT_NEWS_STARTING_FROM_NUMBER = "SELECT * FROM news ORDER BY date_added DESC LIMIT ? OFFSET ?";

	//private Semaphore sem = new Semaphore(1);


	@Override
	public int getTotalNewsAmount() throws DaoException {
		try	(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(Q_GET_COUNT_OF_ALL_NEWS);
				ResultSet rs = statement.executeQuery()) {

			rs.next();
			return rs.getInt(1);

		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<News> getCountNewsStartingFrom(int count, int from) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement statement = connection.prepareStatement(Q_GET_COUNT_NEWS_STARTING_FROM_NUMBER)) {
			int dbOffset = (from-1)*count; //  1*(10-1)
			statement.setInt(1,count);
			statement.setInt(2,dbOffset);

			try (ResultSet rs = statement.executeQuery()) {
				List<News> newsList = new ArrayList<>();
				while (rs.next()) {
					News currNews = new News();
					currNews.setIdNews(rs.getInt(DB_ID));
					currNews.setTitle(rs.getString(DB_TITLE));
					currNews.setBrief(rs.getString(DB_BRIEF));
					currNews.setContent(rs.getString(DB_CONTENT));
					currNews.setNewsDate(rs.getDate(DB_DATE).toLocalDate());
					newsList.add(currNews);

				}
				return newsList;
			}

		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<News> getList() throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 	PreparedStatement statement = connection.prepareStatement(Q_GET_ALL_NEWS_BY_DATE_DESC);
			 	ResultSet rs = statement.executeQuery()) {

			List<News> newsList = new ArrayList<>();
			while (rs.next()) {
				News currNews = new News();
				currNews.setIdNews(rs.getInt(DB_ID));
				currNews.setTitle(rs.getString(DB_TITLE));
				currNews.setBrief(rs.getString(DB_BRIEF));
				currNews.setContent(rs.getString(DB_CONTENT));
				currNews.setNewsDate(rs.getDate(DB_DATE).toLocalDate());
				newsList.add(currNews);
			}
			return newsList;

		} catch (SQLException | ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}
	@Override
	public News fetchById(int id) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_GET_NEWS_BY_ID)) {

			statement.setInt(1,id);
			try (ResultSet rs = statement.executeQuery()) {
				if(!rs.isBeforeFirst()) {
					throw new DaoException("no news with such id in db");
				}

				rs.next();
				News newsFound = new News(id);
				newsFound.setTitle(rs.getString(DB_TITLE));
				newsFound.setBrief(rs.getString(DB_BRIEF));
				newsFound.setContent(rs.getString(DB_CONTENT));
				newsFound.setNewsDate(rs.getDate(DB_DATE).toLocalDate());

				return newsFound;
			} catch (SQLException ex) {
				throw new DaoException(ex);
			}

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public int addNews(News news) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_INSERT_NEWS)) {

			connection.setAutoCommit(false);

			statement.setString(1,news.getTitle());
			statement.setDate(2,Date.valueOf(news.getNewsDate()));
			statement.setString(3,news.getBrief());
			statement.setString(4,news.getContent());
			statement.setInt(5,news.getAuthorId());
			statement.setInt(6,news.getStatusId());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				connection.rollback();
				connection.setAutoCommit(true);
				throw new DaoException("error updating news in db");
			}
			connection.commit();
			connection.setAutoCommit(true);

			return 0;

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void updateNews(News news) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_UPDATE_NEWS_BY_ID)) {

			connection.setAutoCommit(false);

			statement.setString(1,news.getTitle());
			statement.setString(2,news.getBrief());
			statement.setString(3,news.getContent());
			statement.setDate(4, Date.valueOf(news.getNewsDate()));
			statement.setInt(5,news.getIdNews());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				connection.rollback();
				connection.setAutoCommit(true);
				throw new DaoException("error updating news in db");
			}
			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public void deleteNews(int[] newsIds) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement statement = connection.prepareStatement(Q_DELETE_IDS)) {

			connection.setAutoCommit(false);
			//sem.acquire();

			for(int currId : newsIds) {
				statement.setInt(1,currId);
				statement.addBatch();
			}

			int[] affectedRecords = statement.executeBatch();
			for (int affectedRecord : affectedRecords) {
				if (affectedRecord != 1) {
					connection.rollback();
					connection.setAutoCommit(true);
					throw new DaoException("error deleting news from db");
				}
			}
			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		}



	}

}
