package sk.ness.academy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
class AuthorServiceImplTest {
    @Mock
    private AuthorDAO authorDAO;
    @InjectMocks
    private AuthorServiceImpl authorService;
    private List<Author> authors;
    private List<AuthorStats> authorStats;
    @BeforeEach
    public void init() {
        System.out.println("=== Testing author service ===");
        final Author author1 = new Author();
        author1.setName("TestUser1");
        final Author author2 = new Author();
        author2.setName("TestUser2");
        authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        final AuthorStats stats1 = new AuthorStats();
        stats1.setAuthorName("TestUser1");
        stats1.setArticleCount(11);
        final AuthorStats stats2 = new AuthorStats();
        stats2.setAuthorName("TestUser2");
        stats2.setArticleCount(17);
        authorStats = new ArrayList<>();
        authorStats.add(stats1);
        authorStats.add(stats2);
    }

    @Test
    void findAll() {
        Mockito.when(authorDAO.findAll()).thenReturn(authors);
        final List<Author> authors = authorService.findAll();
        Assertions.assertEquals(2, authors.size());
    }
    @Test
    void findAllEmpty() {
        Mockito.when(authorDAO.findAll()).thenReturn(new ArrayList<>());
        final List<Author> authors = authorService.findAll();
        Assertions.assertEquals(0, authors.size());
    }
    @Test
    void getAuthorStats() {
        Mockito.when(authorDAO.getAuthorStats()).thenReturn(authorStats);
        final List<AuthorStats> authorStats = authorService.getAuthorStats();
        Assertions.assertEquals(2, authorStats.size());
        Assertions.assertEquals("TestUser1", authorStats.get(0).getAuthorName());
        Assertions.assertEquals(11, authorStats.get(0).getArticleCount());
        Assertions.assertEquals("TestUser2", authorStats.get(1).getAuthorName());
        Assertions.assertEquals(17, authorStats.get(1).getArticleCount());
    }
}