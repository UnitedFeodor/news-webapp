package by.htp.ex.util.validation.impl;

import by.htp.ex.bean.News;
import by.htp.ex.util.validation.INewsValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class NewsValidatorImpl implements INewsValidator {

    private List<String> errors;
    private News news;

    private final int FIELD_COUNT = 7;

    public NewsValidatorImpl() {
        this.errors = new ArrayList<>(FIELD_COUNT);
    }

    @Override
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public INewsValidator setNews(News news) {
        this.news = new News(news);
        errors.clear();
        return this;
    }

    @Override
    public News getNews() {
        return new News(news);
    }

    private final String ID_ERR = "id error";
    // correct if id >= 0
    @Override
    public INewsValidator checkId() {
        if(news.getIdNews() < 0) {
            errors.add(ID_ERR);
        }
        return this;
    }

    private final String TITLE_ERR = "title error";
    // correct if not empty
    @Override
    public INewsValidator checkTitle() {
        String title = news.getTitle();
        if (title == null || title.isEmpty()) {
            errors.add(TITLE_ERR);
        }
        return this;
    }

    private final String BRIEF_ERR = "brief error";
    // correct if not empty
    @Override
    public INewsValidator checkBrief() {
        String brief = news.getBrief();
        if (brief == null || brief.isEmpty()) {
            errors.add(BRIEF_ERR);
        }
        return this;
    }

    private final String CONTENT_ERR = "content error";
    // correct if not empty
    @Override
    public INewsValidator checkContent() {
        String content = news.getContent();
        if (content == null || content.isEmpty()) {
            errors.add(CONTENT_ERR);
        }
        return this;
    }

    private final String DATE_ERR = "date error";
    //not null and not in the future
    @Override
    public INewsValidator checkDate() {
        LocalDate dateAdded = news.getNewsDate();
        if (dateAdded == null || dateAdded.isAfter(LocalDate.now())) {
            errors.add(DATE_ERR);
        }
        return this;
    }

    private final String AUTHOR_ID_ERR = "author id error";
    // correct if positive
    @Override
    public INewsValidator checkAuthorId() {
        if(news.getAuthorId() < 0) {
            errors.add(AUTHOR_ID_ERR);
        }
        return this;
    }

    private final String STATUS_ID_ERR = "status id error";
    // correct if positive
    @Override
    public INewsValidator checkStatusId() {
        if(news.getStatusId() < 0) {
            errors.add(STATUS_ID_ERR);
        }
        return this;
    }

    @Override
    public INewsValidator checkAll() {
        return this.checkId().checkTitle().checkBrief().checkContent().checkDate().checkAuthorId().checkStatusId();
    }

    @Override
    public INewsValidator checkAllExceptId() {
        return this.checkTitle().checkBrief().checkContent().checkDate().checkAuthorId().checkStatusId();
    }

    @Override
    public boolean isValid() {
        return errors.isEmpty();
    }
}
