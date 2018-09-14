package com.mercateo.spring.boot.postgres.tests.root;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercateo.spring.boot.postgres.tests.EmbeddedPostgresForWebTest;
import com.mercateo.spring.boot.postgres.tests.TestApplication;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedPostgresForWebTest
public class RootResourceTest {

    @Inject
    private TestRestTemplate restTemplate;

    @Test
    public void test_get_root() throws Exception {

        // given

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/",
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
