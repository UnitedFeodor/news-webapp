package by.htp.ex.dao.impl;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;

public class NewsDAO implements INewsDAO {

	public static final String DB_ID = "id";
	public static final String DB_TITLE = "title";
	public static final String DB_DATE = "date_added";
	public static final String DB_BRIEF = "brief";
	public static final String DB_CONTENT = "content";
	
	
	private static final String Q_INSERT_NEWS = "INSERT INTO news (title, date_added, brief, content, authors_user_id, news_status_id) VALUES (?,?,?,?,?,?)";

	private static final String Q_DELETE_IDS = "DELETE FROM news WHERE id = ?";
	private static final String Q_UPDATE_NEWS_BY_ID = "UPDATE news SET title = ?, brief = ?, content = ? WHERE id = ?";
	private static final String Q_GET_ALL_NEWS = "SELECT * FROM news";
	private static final String Q_GET_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
	private static final String Q_GET_ALL_NEWS_BY_DATE_DESC = "SELECT * FROM news ORDER BY date_added DESC";

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock writeLock = lock.writeLock();
	private Lock readLock = lock.readLock();

	//private Semaphore sem = new Semaphore(1);

	@Override
	public List<News> getLatestList(int count) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
				PreparedStatement statement = connection.prepareStatement(Q_GET_ALL_NEWS_BY_DATE_DESC);
				ResultSet rs = statement.executeQuery()) {

			readLock.lock();

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
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public List<News> getList() throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 	PreparedStatement statement = connection.prepareStatement(Q_GET_ALL_NEWS);
			 	ResultSet rs = statement.executeQuery()) {

			readLock.lock();

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
		} finally {
			readLock.unlock();
		}
	}
	@Override
	public News fetchById(int id) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_GET_NEWS_BY_ID)) {

			readLock.lock();

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
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int addNews(News news) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_INSERT_NEWS)) {

			writeLock.lock();

			statement.setString(1,news.getTitle());
			statement.setDate(2,Date.valueOf(news.getNewsDate()));
			statement.setString(3,news.getBrief());
			statement.setString(4,news.getContent());
			statement.setInt(5,news.getAuthorId());
			statement.setInt(6,news.getStatusId());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new DaoException("error updating news in db");
			}
			return 0;

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void updateNews(News news) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_UPDATE_NEWS_BY_ID)) {

			writeLock.lock();

			statement.setString(1,news.getTitle());
			statement.setString(2,news.getBrief());
			statement.setString(3,news.getContent());

			statement.setInt(4,news.getIdNews());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new DaoException("error updating news in db");
			}

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public void deleteNews(int[] newsIds) throws DaoException {
		try (Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 PreparedStatement statement = connection.prepareStatement(Q_DELETE_IDS)) {

			writeLock.lock();
			//sem.acquire();

			for(int currId : newsIds) {
				statement.setInt(1,currId);
				statement.addBatch();
			}

			int[] affectedRecords = statement.executeBatch();
			for (int affectedRecord : affectedRecords) {
				if (affectedRecord != 1) {
					throw new DaoException("error deleting news from db");
				}
			}

		} catch (SQLException | ConnectionPoolException | DaoException e) {
			throw new DaoException(e);
		} finally {
			writeLock.unlock();
			//sem.release();
		}



	}

}
