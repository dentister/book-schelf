package com.schnitzel.book.schelf;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schnitzel.book.schelf.book.schelf.api.delegate.BookSchelfServiceApi;
import com.schnitzel.book.schelf.book.schelf.client.invoker.ApiClient;
import com.schnitzel.book.schelf.config.TestHttpClientConfig;

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
    }

    @Test
    public void controllerIsOkTest() {
        assertNotNull(api);
        assertNotNull(jdbcTemplate);
    }
}