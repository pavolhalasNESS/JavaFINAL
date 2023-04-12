package sk.ness.academy.service;

import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
	  return this.articleDAO.findByID(articleId);
  }

  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      List<Article> listArticles = List.of(mapper.readValue(jsonArticles, Article[].class));
      listArticles.forEach(article -> articleDAO.persist(article));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void deleteByID(Integer articleId) {
    this.articleDAO.deleteByID(articleId);
  }

  @Override
  public List<Article> searchArticle(String searchText) {
    return this.articleDAO.searchArticle(searchText);
  }

}
