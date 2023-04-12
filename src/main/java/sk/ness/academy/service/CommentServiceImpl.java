package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Comment;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentDAO commentDAO;

    @Resource
    private ArticleDAO articleDAO;

    @Override
    public List<Comment> findAll() {
        return this.commentDAO.findAll();
    }

    @Override
    public List<Comment> findByIDArticle(final Integer articleId) {
        return this.commentDAO.findByIDArticle(articleId);
    }

    @Override
    public void createComment(Integer articleId, Comment comment) {
        this.commentDAO.createArticleComment(comment,articleId);
    }

    @Override
    public void deleteComment(Integer commentId, Integer articleId) {
        this.commentDAO.deleteComment(commentId, articleId);
    }

}
