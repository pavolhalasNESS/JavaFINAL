package sk.ness.academy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.service.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTest {

    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private CommentServiceImpl commentService;

    private List<Comment> comments;

    private List<Comment> comments1 = new ArrayList<>();

    private int ArticleID = 4;

    @BeforeEach
    private void init() {
        System.out.println("=== Testing comment service ===");
        final Comment comment1 = new Comment();

        comment1.setId(1);
        comment1.setText("first comment");
        comment1.setAuthor("TestUser1");
        comment1.setArticleId( ArticleID);

        comments = new ArrayList<>();
        comments.add(comment1);
    }

    @Test
    void findAll() {
        Mockito.when(commentDAO.findAll()).thenReturn(comments);
        List<Comment> comments = commentService.findAll();
        Assertions.assertEquals(1, comments.size());
        Assertions.assertEquals(1, comments.get(0).getId());
        Assertions.assertEquals(4, comments.get(0).getArticleId());
        Assertions.assertEquals("first comment", comments.get(0).getText());
        Assertions.assertEquals("TestUser1", comments.get(0).getAuthor());
    }

    @Test
    void findAllEmpty() {
        Mockito.when(commentDAO.findAll()).thenReturn(new ArrayList<>());
        List<Comment> comments = commentService.findAll();
        Assertions.assertEquals(0, comments.size());
    }

    @Test
    void findByIDArticle() {
        Mockito.when(commentDAO.findByIDArticle( ArticleID)).thenReturn(comments);
        List<Comment> comments = commentService.findByIDArticle( ArticleID);
        Assertions.assertEquals(1, comments.size());
    }

    @Test
    void createComment() {
        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setText("second comment");
        comment2.setAuthor("TestUser2");
        comment2.setArticleId(ArticleID);
        commentService.createComment(ArticleID,comment2);
        Mockito.verify(commentDAO, Mockito.times(1)).createArticleComment(comment2,ArticleID);
    }

    @Test
    void deleteComment() {
        commentService.deleteComment(1,ArticleID);
        Mockito.verify(commentDAO, Mockito.times(1)).deleteComment(1,ArticleID);
    }
}