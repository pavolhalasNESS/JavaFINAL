package sk.ness.academy.dto;

import java.util.Date;
import java.util.List;

import sk.ness.academy.domain.Comment;

public class ArticleDTO
{
    private ArticleDTO articleDTO;

    private List<Comment> comments;
    private Integer id;
    private String title;
    private String text;
    private String author;
    private Date createTimestamp;

    public ArticleDTO(Integer id, String title, String text, String author, Date createTimestamp) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
        this.createTimestamp = createTimestamp;
    }

    public ArticleDTO getArticle() {
        return articleDTO;
    }

    public void setArticle(ArticleDTO articleDTO) {
        this.articleDTO = articleDTO;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
}
