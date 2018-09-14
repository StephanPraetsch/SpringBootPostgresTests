package com.mercateo.spring.boot.postgres.tests.users.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercateo.spring.boot.postgres.tests.EmbeddedPostgresForWebTest;
import com.mercateo.spring.boot.postgres.tests.TestApplication;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;
import com.mercateo.spring.boot.postgres.tests.users.rest.CreateUserJson.CreateUserJsonBuilder;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedPostgresForWebTest
public class UsersResourceTest {

    @Inject
    private TestRestTemplate restTemplate;

    @After
    public void after() {
        resetHeaders();
    }

    private void resetHeaders() {
        restTemplate.getRestTemplate().setInterceptors(Collections.emptyList());
    }

    private CreateUserJsonBuilder defaultCreateJson() {
        return CreateUserJson.builder().id(UUID.fromString("1-1-1-1-1"))
                .name("name")
                .birth(OffsetDateTime.of(2018, 9, 14, 12, 10, 51, 0, ZoneOffset.UTC));
    }

    private ResponseEntity<String> post(CreateUserJson json) {
        return restTemplate.postForEntity(
                "/users/" + json.getId(),
                json,
                String.class,
                Collections.emptyMap());
    }

    private void put(UpdateUserJson json) {
        restTemplate.put(
                "/users/" + json.getId(),
                json);
    }

    private ResponseEntity<User> get(UserId id) {
        return restTemplate.getForEntity(
                "/users/" + id.getValue(),
                User.class);
    }

    @Test
    @DirtiesContext
    public void test_get_users() throws Exception {

        // given

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/users",
                String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void test_create() throws Exception {

        // given
        CreateUserJson json = defaultCreateJson().build();

        // when
        ResponseEntity<String> response = post(json);

        // then
        assertThat(response.getStatusCode())
                .as(response.getBody())
                .isEqualTo(HttpStatus.OK);

    }

    @Test
    public void test_create_and_get() throws Exception {

        // given
        UserId id = new UserId(UUID.fromString("1-1-1-1-2"));
        post(defaultCreateJson().id(id.getValue()).build());

        // when
        put(UpdateUserJson.builder().id(id).name("different name").build());

        // then
        ResponseEntity<User> response = get(id);
        assertThat(response)
                .isEqualTo(HttpStatus.OK)
                .extracting(r -> (User) r.getBody())
                .isEqualTo("different name");

    }

    @Test
    @DirtiesContext
    public void test_create_twice_the_same() throws Exception {

        // given

        CreateUserJson json = defaultCreateJson().build();
        post(json);

        // when
        ResponseEntity<String> response = post(json);

        // then
        assertThat(response.getStatusCode())
                .as(response.getBody())
                .isEqualTo(HttpStatus.OK);

    }

    @Test
    @DirtiesContext
    public void test_create_id_in_path_differs_from_json() throws Exception {

        // given
        CreateUserJson json = defaultCreateJson()
                .id(UUID.fromString("99999999-9999-9999-9999-999999999999"))
                .build();

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/users/00000000-0000-0000-0000-000000000000",
                json,
                String.class,
                Collections.emptyMap());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .contains(
                        "path param id 00000000-0000-0000-0000-000000000000 conflicts with payload id 99999999-9999-9999-9999-999999999999");

    }

}
