package sk.ness.academy.dao;

import sk.ness.academy.domain.Comment;
import java.util.List;

public interface CommentDAO {

    List<Comment> findByIDArticle(Integer articleId);

    List<Comment> findAll();

    void persist(Comment comment);

    void createArticleComment(Comment comment,Integer articleId);

    void deleteComment(Integer commentId, Integer articleId);
}
