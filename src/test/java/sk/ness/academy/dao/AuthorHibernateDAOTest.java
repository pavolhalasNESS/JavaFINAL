package sk.ness.academy.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import sk.ness.academy.config.TestDatabaseConfig;
import sk.ness.academy.dto.Author;
import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = { TestDatabaseConfig.class, AuthorHibernateDAO.class })
@Transactional
class AuthorHibernateDAOTest {
    @Autowired
    private AuthorDAO authorDAO;
    @BeforeEach
    public void beforeEach() {
        System.out.println("=== Testing author DAO ===");
    }
    @Test
    void findAll() {
        final List<Author> authors = authorDAO.findAll();
        Assertions.assertTrue(authors.size()  > 0 );
        Assertions.assertEquals("Emil Forslund", authors.get(0).getName());
    }
}