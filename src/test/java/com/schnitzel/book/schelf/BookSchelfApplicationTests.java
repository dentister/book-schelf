package com.schnitzel.book.schelf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schnitzel.book.schelf.book.schelf.api.model.BookDto;
import com.schnitzel.book.schelf.book.schelf.api.model.BookWithKeyDto;
import com.schnitzel.book.schelf.book.schelf.client.api.BookSchelfServiceApi;
import com.schnitzel.book.schelf.book.schelf.client.invoker.ApiClient;
import com.schnitzel.book.schelf.config.TestHttpClientConfig;
import com.schnitzel.book.schelf.utils.TestUtil;

@Import(BookSchelfApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestHttpClientConfig.class)
class BookSchelfApplicationTests {

    @Autowired
    private BookSchelfServiceApi api;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApiClient apiClient;

    @BeforeEach
    public void setup() {
        apiClient.setBasePath("http://localhost:8080");
        jdbcTemplate.execute("delete from books");
    }

    @Test
    public void controllerIsOkTest() {
        assertNotNull(api);
        assertNotNull(jdbcTemplate);
    }
    
    @Test
    public void addBookTest() {
        BookWithKeyDto src = book(1L, "1", "1", "1");
        
        api.create(src);
        BookWithKeyDto res = api.getAll().get(0);
        
        assertEquals(src, res);
    }
    
    @Test
    public void addBookFailedTest() {
        api.create( book(1L, "1", "1", "1"));
        
        TestUtil.testError(HttpStatus.BAD_REQUEST.value(), () -> api.create( book(1L, "2", "2", "2") ));
        TestUtil.testError(HttpStatus.BAD_REQUEST.value(), () -> api.create( book(null, "3", "3", "3") ));
        TestUtil.testError(HttpStatus.BAD_REQUEST.value(), () -> api.create( book(4L, null, "4", "4") ));
        TestUtil.testError(HttpStatus.BAD_REQUEST.value(), () -> api.create( book(5L, "5", null, null) ));
        
        assertEquals(1, api.getAll().size());
    }
    
    @Test
    public void updateBookTest() {
        BookWithKeyDto src = book(1L, "1", "1", "1");
        
        api.create(src);
        api.update(1L, lightBook("2", "2", "2"));
        
        BookWithKeyDto exp = src.name("2").author("2").annotation("2");
        List<BookWithKeyDto> res = api.getAll();

        assertEquals(1, res.size());
        assertEquals(exp, res.get(0));
    }
    
    @Test
    public void updateBookFailedTest() {
        api.create( book(1L, "1", "1", "1"));
        
        TestUtil.testError(HttpStatus.NOT_FOUND.value(), () -> api.update( 2L, lightBook("2", "2", "2") ));
        
        assertEquals(1, api.getAll().size());
    }
    
    public void deleteBookTest() {
        api.create( book(1L, "1", "1", "1"));
        api.delete(1L);
        
        assertEquals(0, api.getAll().size());
    }
    
    public void deleteBookFailedTest() {
        api.create( book(1L, "1", "1", "1"));
        api.delete(1L);
        TestUtil.testError(HttpStatus.NOT_FOUND.value(), () -> api.delete( 2L));
        
        assertEquals(0, api.getAll().size());
    }
    
    public void searchTest() {
        var book1 = book(1L, "Test1", "Author1", "1");
        var book2 = book(2L, "Test2", "Author1", null);
        var book3 = book(3L, "Test3", "Author5", null);
        
        api.create( book1 );
        api.create( book2 );
        api.create( book3 );
        
        List<BookWithKeyDto> res = api.search( book(null, null, "Author1", null) );
        assertTrue(res.contains(book1));
        assertTrue(res.contains(book2));
        assertEquals(2, res.size());
        
        res = api.search( book(1L, null, "Author1", null) );
        assertTrue(res.contains(book1));
        assertEquals(1, res.size());
        
        res = api.search( book(null, "Test3", null, null) );
        assertTrue(res.contains(book3));
        assertEquals(1, res.size());
    }
    
    private BookDto lightBook(String name, String author, String annotation) {
        return new BookDto()
                .name(name)
                .author(author)
                .annotation(annotation);
    }
    
    private BookWithKeyDto book(Long isbn, String name, String author, String annotation) {
        return new BookWithKeyDto()
                .isbn(isbn)
                .name(name)
                .author(author)
                .annotation(annotation);
    }
    
}