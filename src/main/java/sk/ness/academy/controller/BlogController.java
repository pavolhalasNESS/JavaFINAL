package sk.ness.academy.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.RequestException;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {
  @Resource
  private ArticleService articleService;
  @Resource
  private AuthorService authorService;
  @Resource
  private CommentService commentService;

  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
	  return this.articleService.findAll();
  }
  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
    return this.authorService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable final Integer articleId) {
    Article article = this.articleService.findByID(articleId);
    if (article == null) {
      throw new RequestException(HttpStatus.NOT_FOUND, "Article with ID: " + articleId + " does not exist");
    } else return article;
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable String searchText) {
    if ( this.articleService.searchArticle(searchText).size() == 0) {
      throw new RequestException(HttpStatus.NOT_FOUND, "Article with searchText does not exist");
    } else return this.articleService.searchArticle(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody final Article article) {
    if(article ==null || article.getTitle()  ==null|| article.getAuthor() ==null || article.getText() ==null ){
      throw new RequestException(HttpStatus.BAD_REQUEST, "Article does not contain all required fields (author, title or text)");
    }else this.articleService.createArticle(article);
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
    if(this.authorService.findAll().size()==0){
      throw new RequestException(HttpStatus.NOT_FOUND, "Database with articles is empty");}
    else
    return this.authorService.getAuthorStats();
  }

  @RequestMapping(value= "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
    Article article = this.articleService.findByID(articleId);
    if (article == null) {throw new RequestException(HttpStatus.NOT_FOUND, "Article with ID: " + articleId + " does not exist, so you cant this article");}
    else this.articleService.deleteByID(articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public void addComment(@RequestBody final Comment comment, @PathVariable final Integer articleId) {
    Article article = this.articleService.findByID(articleId);
    if (article == null) {
      throw new RequestException(HttpStatus.NOT_FOUND, "Article with ID: " + articleId + " does not exist, so you cant add comment");
    }else this.commentService.createComment(articleId, comment);
  }

  @RequestMapping(value = "articles/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteArticleComment(@PathVariable("articleId") Integer articleId, @PathVariable("commentId") Integer commentId) {
    Article article = this.articleService.findByID(articleId);
    List<Comment> comments = (this.commentService.findByIDArticle(articleId));
    Comment comment = comments.get(0);
    if (article == null) {
      throw new RequestException(HttpStatus.NOT_FOUND, "Article with ID: " + articleId + " does not exist, so you cant delete comment");
    }else {
      if(comment== null){
        throw new RequestException(HttpStatus.NOT_FOUND, "Comment with ID: " + commentId + " does not exist, so you cant delete this comment from request");
      }else{
        this.commentService.deleteComment(commentId, articleId);
      }}
  }

}
