package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleDTO;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("SELECT  a.id as id,a.title as title,a.text as text, a.author as author  FROM articles a ")
            .addScalar("id", StandardBasicTypes.INTEGER)
            .addScalar("title", StringType.INSTANCE)
            .addScalar("text", StringType.INSTANCE)
            .addScalar("author", StringType.INSTANCE)
            .setResultTransformer(new AliasToBeanResultTransformer(Article.class)).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  @Override
  public void deleteByID(Integer articleId) {
    Article article = (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
    this.sessionFactory.getCurrentSession().delete(article);
   // this.sessionFactory.getCurrentSession().flush();
  }

  @Override
  public List<Article> searchArticle(String searchText) {
    return this.sessionFactory.getCurrentSession()
            .createSQLQuery("select * from articles where ((author like :pattern1)  or (text like :pattern2) or (title like :pattern3))")
            .setParameter("pattern1","%" + searchText + "%")
            .setParameter("pattern2","%" + searchText + "%")
            .setParameter("pattern3","%" + searchText + "%")
            .addEntity(Article.class).list();
  }

}
