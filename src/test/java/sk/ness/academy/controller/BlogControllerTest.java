package sk.ness.academy.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;
import javax.annotation.Resource;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.util.List;

@SpringBootTest
class BlogControllerTest {

    @Resource
    private ArticleService articleService;
    @Resource
    private CommentService commentService;

    @Resource
    private AuthorService authorService;

    private int getFirstID (){
        List<Article> articles = this.articleService.findAll();
        int articleId = articles.get(0).getId();
        return articleId;
    }

    @Test
    void getAllArticles() {
        given().when().get("articles").then().assertThat().statusCode(200);
    }

    @Test
    void getAllAuthors() {
        given().when().get("authors").then().assertThat().statusCode(200);
    }
    @Test
    void getArticle() {
        given().when().get("articles/"+getFirstID()).then().assertThat().statusCode(200);
    }

    @Test
    void addArticle() throws JSONException {
        JSONObject articleJSON = new JSONObject();
        articleJSON.put("author", "Testing User");
        articleJSON.put("title", "Testing Title");
        articleJSON.put("text", "TestingSpring");
        String testingArticle=articleJSON.toString();
        given().contentType(ContentType.JSON).body(testingArticle).when().put("articles")
                .then().assertThat().statusCode(200);
    }

    @Test
    void searchArticle() throws JSONException {
        addArticle();
        String searchText ="TestingSpring";
        given().when().get("articles/search/"+searchText).then().assertThat().statusCode(200);
    }

    @Test
    void authorStats() {
        given().when().get("authors/stats").then().assertThat().statusCode(200);
    }

    @Test
    void deleteArticle() {
        List<Article> articles = this.articleService.findAll();
        given().when().delete("articles/" + getFirstID()).then().statusCode(200);
    }

    @Test
    void addComment() throws JSONException {
        JSONObject commentJSON = new JSONObject();
        commentJSON.put("author", "Testing Commentator");
        commentJSON.put("text", "Testing Comment");
        String testingComment=commentJSON.toString();
        given().contentType(ContentType.JSON).body(testingComment).when().put("articles/"+getFirstID()+"/comments")
                .then().assertThat().statusCode(200);
    }

    @Test
    void deleteArticleComment() throws JSONException {
        addComment();
        List<Comment> comments = this.commentService.findByIDArticle(getFirstID());
        given().when().delete("articles/" + getFirstID() + "/comments/" + comments.get(0).getId()).then().statusCode(200);
    }

}