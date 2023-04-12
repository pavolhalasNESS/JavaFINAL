package sk.ness.academy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;
import sk.ness.academy.service.ArticleServiceImpl;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceImplTest {

    @Mock
    private ArticleDAO articleDAO;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private List<Article> articles;
    final Integer firstID = 37;
    final Integer secondID = 54;

    final String searchText = "amazing";

    @BeforeEach
    private void init() {
        System.out.println("=== Testing article service ===");
        final Article article1 = new Article();
        article1.setId(firstID);
        article1.setTitle("First book");
        article1.setAuthor("TestUser1");
        article1.setText("amazing book");

        final Article article2 = new Article();
        article2.setId(secondID);
        article2.setTitle("Second book");
        article2.setAuthor("TestUser2");
        article2.setText("boring book");

        articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
    }

    @Test
    void findByID() {
        Mockito.when(articleDAO.findByID(firstID)).thenReturn(articles.get(0));
        Article article1 = articleService.findByID(firstID);
        Assertions.assertEquals(37, article1.getId());
        Assertions.assertEquals("First book", article1.getTitle());
        Assertions.assertEquals("TestUser1", article1.getAuthor());
        Assertions.assertEquals("amazing book", article1.getText());
    }

    @Test
    void findAll() {
        Mockito.when(articleDAO.findAll()).thenReturn(articles);
        List<Article> articles = articleService.findAll();
        Assertions.assertEquals(2, articles.size());

        Assertions.assertEquals(37, articles.get(0).getId());
        Assertions.assertEquals("First book", articles.get(0).getTitle());
        Assertions.assertEquals("TestUser1", articles.get(0).getAuthor());
        Assertions.assertEquals("amazing book", articles.get(0).getText());

        Assertions.assertEquals(54, articles.get(1).getId());
        Assertions.assertEquals("Second book", articles.get(1).getTitle());
        Assertions.assertEquals("TestUser2", articles.get(1).getAuthor());
        Assertions.assertEquals("boring book", articles.get(1).getText());
    }

    @Test
    void findAllEmpty() {
        Mockito.when(articleDAO.findAll()).thenReturn(new ArrayList<>());
        List<Article> articles = articleService.findAll();
        assertEquals(0, articles.size());;
    }

    @Test
    void createArticle() {
        Article article3 = new Article();
        article3.setId(100);
        article3.setTitle("Third book");
        article3.setAuthor("TestUser3");
        article3.setText("average book");
        articleService.createArticle(article3);
        Mockito.verify(articleDAO, Mockito.times(1)).persist(article3);
    }

    @Test
    void searchArticle() {
        Mockito.when(articleDAO.searchArticle(searchText)).thenReturn(articles);
        List<Article> articles = articleService.searchArticle(searchText);
        Assertions.assertTrue(articles.size()  > 0 );
    }

    @Test
    void deleteByID() {
        articleService.deleteByID(firstID);
        Mockito.verify(articleDAO, Mockito.times(1)).deleteByID(firstID);
    }
}