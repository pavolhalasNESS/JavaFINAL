package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dao.CommentDAO;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO{

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public List<Comment> findByIDArticle(final Integer articleId) {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments where articleid = '" + articleId + "'")
                .addEntity(Comment.class).list();
    }

    @Override
    public List<Comment> findAll() {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select * from comments").addEntity(Comment.class).list();
    }

    @Override
    public void persist(Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    public  void createArticleComment(Comment comment,Integer articleId) {
        Article article =this.sessionFactory.getCurrentSession().load(Article.class,articleId);
            this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
            List list= (List) article.getComments();
            list.add(comment);
            article.setComments(list);
    }

    @Override
    public void deleteComment(Integer commentId, Integer articleId) {
        Article article = this.sessionFactory.getCurrentSession().load(Article.class, articleId);
            List comments=article.getComments();
            for (int i = 0; i < comments.size(); i++) {
                Comment comment=(Comment) comments.get(i);
                if (comment.getId()==commentId) {
                    comments.remove(comment);
                    article.setComments(comments);
                }
            }

    }

}
