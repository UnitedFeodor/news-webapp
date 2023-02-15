package by.htp.ex.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_STATUS_DB = 1; // wip
	
	private int idNews = -1;
	private String title = "";
	private String brief = "";
	private String content = "";
	private LocalDate newsDate = LocalDate.now();

	private int authorId = -1;
	private int statusId = DEFAULT_STATUS_DB;



	public News(){}

	public News(int idNews){
		this.idNews = idNews;
	}

	public News(int idNews, String title, String brief, String content, LocalDate newsDate) {
		super();
		this.idNews = idNews;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.newsDate = newsDate;
	}

	public News(News news) {
		this.idNews = news.getIdNews();
		this.title = news.getTitle();
		this.brief = news.getBrief();
		this.content = news.getContent();
		this.newsDate = news.getNewsDate();
		this.authorId = news.getAuthorId();
		this.statusId = news.getStatusId();
	}

	public int getIdNews() {
		return idNews;
	}

	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public LocalDate getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(LocalDate newsDate) {
		this.newsDate = newsDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		News news = (News) o;
		return idNews == news.idNews && authorId == news.authorId && statusId == news.statusId && title.equals(news.title) && brief.equals(news.brief) && content.equals(news.content) && newsDate.equals(news.newsDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idNews, title, brief, content, newsDate, authorId, statusId);
	}
}
