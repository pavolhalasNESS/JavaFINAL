package sk.ness.academy.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, CommentHibernateDAO.class })
@Transactional
class CommentHibernateDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    @BeforeEach
    public void beforeEach() {
        System.out.println("=== Testing comment DAO ===");
    }

    @Test
    void findByIDArticle() {
        final List<Comment> comments = commentDAO.findByIDArticle(2);
        Assertions.assertEquals(1,comments.size()  );
        Assertions.assertEquals(1, comments.get(0).getId());
    }

    @Test
    void findAll() {
        final List<Comment> comments = commentDAO.findAll();
        Assertions.assertTrue(comments.size()  > 0 );
        Assertions.assertEquals(1, comments.get(0).getId());
        Assertions.assertEquals(2, comments.get(1).getId());
}

    @Test
    void createArticleComment() {
        Comment comment = new Comment();;
        comment.setAuthor("TestUser3");
        comment.setText("random comment");
        commentDAO.createArticleComment(comment,4);
        final List<Comment> comments = commentDAO.findByIDArticle(4);
        //final List<Comment> comments = commentDAO.findAll();
        Assertions.assertEquals("random comment", comments.get(0).getText());
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();;
        final int idArticle = 4;
        comment.setAuthor("TestUser4");
        comment.setText("temporary comment");
        commentDAO.createArticleComment(comment,idArticle);
        Assertions.assertFalse((commentDAO.findByIDArticle(4)).isEmpty());
        final int idComment = commentDAO.findByIDArticle(idArticle).get(0).getId();
        commentDAO.deleteComment(idComment,idArticle);
        Assertions.assertTrue((commentDAO.findByIDArticle(4)).isEmpty());
    }
}