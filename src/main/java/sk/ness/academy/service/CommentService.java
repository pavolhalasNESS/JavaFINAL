package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;
import java.util.List;
public interface CommentService {

    List<Comment> findAll();

    List<Comment> findByIDArticle(Integer articleId);

    void createComment(Integer articleId, Comment comment );

    void deleteComment(Integer commentId,Integer articleId);


}
