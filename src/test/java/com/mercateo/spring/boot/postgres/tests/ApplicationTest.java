package com.mercateo.spring.boot.postgres.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercateo.spring.boot.postgres.tests.users.persistence.UsersService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    UsersService arbitraryService;

    @Test
    public void contextLoads() {
        assertThat(arbitraryService).isNotNull();
    }

}
