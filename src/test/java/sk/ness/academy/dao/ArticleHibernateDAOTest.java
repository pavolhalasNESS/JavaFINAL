package sk.ness.academy.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.domain.Article;
import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, ArticleHibernateDAO.class })
@Transactional
class ArticleHibernateDAOTest {

    @Autowired
    private ArticleDAO articleDAO;

    @BeforeEach
    public void beforeEach() {
        System.out.println("=== Testing article DAO ===");
    }

    @Test
    void findByID() {
        final Article article = articleDAO.findByID(4);
        Assertions.assertEquals("Extending the Stream API to Maps", article.getTitle() );
        Assertions.assertEquals("Emil Forslund",article.getAuthor());
    }

    @Test
    void findAll() {
        final List<Article> articles = articleDAO.findAll();
        Assertions.assertTrue(articles.size()  > 0 );
        Assertions.assertEquals(4, articles.get(articles.size()-1).getId());
        Assertions.assertEquals("Extending the Stream API to Maps", articles.get(articles.size()-1).getTitle());
    }

    @Test
    void persist() {
        Article article = new Article();
        article.setId(10);
        article.setAuthor("TestUser3");
        article.setText("random text");
        article.setTitle("Testing Article");
        articleDAO.persist(article);
        Assertions.assertEquals("Testing Article",articleDAO.findByID(10).getTitle());
    }

    @Test
    void deleteByID() {
        Article article = new Article();
        article.setId(11);
        article.setAuthor("TestUser3");
        article.setText("random text");
        article.setTitle("Testing Article");
        articleDAO.persist(article);
        articleDAO.deleteByID(4);
        Assertions.assertNull(articleDAO.findByID(4));
    }
}