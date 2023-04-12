package sk.ness.academy.domain;

import java.util.Date;
import javax.persistence.*;
@Entity
@Table(name = "comments")
@SequenceGenerator(name = "comments_seq_store", sequenceName = "comments_seq", allocationSize = 1)
public class Comment {

    public Comment() {
        this.createTimestamp = new Date();
    }
    @Id
    @Column(name = "commentId", unique = true, nullable = false, precision = 10, scale = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq_store")
    private Integer commentId;

    @Column(name = "text", length = 2000)
    private String text;

    @Column(name = "author", length = 250)
    private String author;

    @Column(name = "create_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Column(name = "articleId")
    private Integer articleId;

    public Integer getId() {
        return commentId;
    }

    public void setId(Integer commentId) {
        this.commentId = commentId;
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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
