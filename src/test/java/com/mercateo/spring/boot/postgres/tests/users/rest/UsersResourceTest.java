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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mercateo.spring.boot.postgres.tests.EmbeddedPostgresForWebTest;
import com.mercateo.spring.boot.postgres.tests.TestApplication;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;
import com.mercateo.spring.boot.postgres.tests.users.persistence.UsersService;

import lombok.AllArgsConstructor;
import lombok.Data;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedPostgresForWebTest
public class UsersResourceTest {

    @Inject
    private TestRestTemplate restTemplate;

    @Inject
    private UsersService userService;

    private UserId userId = new UserId(UUID.fromString("1-1-1-1-2")); 

    @After
    public void after() {
        resetHeaders();
    }

    private void resetHeaders() {
        restTemplate.getRestTemplate().setInterceptors(Collections.emptyList());
    }

    private CreateUserJson createJson(String name) {
        return CreateUserJson.builder()
                .id(userId.getId())
                .name(name)
                .birth(OffsetDateTime.of(2018, 9, 14, 12, 10, 51, 0, ZoneOffset.UTC))
                .build();
    }

    private CreateUserJson createJson(UUID id) {
        return CreateUserJson.builder()
                .id(id)
                .name("name")
                .birth(OffsetDateTime.of(2018, 9, 14, 12, 10, 51, 0, ZoneOffset.UTC))
                .build();
    }

    private ResponseEntity<String> post(CreateUserJson json) {
        return restTemplate.postForEntity(
                "/users/" + json.getId(),
                json,
                String.class,
                Collections.emptyMap());
    }

    private ResponseEntity<String> put(UpdateUserJson json) {
        return restTemplate.exchange(
                "/users/" + json.getId().getId(),
                HttpMethod.PUT,
                new HttpEntity<>(json),
                String.class);
    }

    private ResponseEntity<ResponseUserJson> get(UserId id) {
        return restTemplate.getForEntity(
                "/users/" + id.getId(),
                ResponseUserJson.class);
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
    @DirtiesContext
    public void test_create() throws Exception {

        // given
        CreateUserJson json = createJson("test_create");

        // when
        ResponseEntity<String> response = post(json);

        // then
        assertThat(response.getStatusCode())
                .as(response.getBody())
                .isEqualTo(HttpStatus.OK);
        assertThat(userService.getAll()).hasSize(1);

    }

    @Data
    @AllArgsConstructor
    public static class ResponseUserJson {
        UUID id;

        String name;
    }

    @Test
    @DirtiesContext
    public void test_get() throws Exception {

        // given
        User user = new User(userId, "test_get", OffsetDateTime.now());
        userService.create(user);

        // when
        ResponseEntity<ResponseUserJson> response = get(userId);

        // then
        assertThat(response.getBody())
                .isEqualTo(new ResponseUserJson(userId.getId(), "test_get"));

    }

    @Test
    @DirtiesContext
    public void test_update() throws Exception {

        // given
        User user = new User(userId, "test_update", OffsetDateTime.now());
        userService.create(user);

        // when
        ResponseEntity<String> response = put(UpdateUserJson.builder()
                .id(userId).name("different name").build());

        // then
        assertThat(response.getStatusCode()).as(response.getBody())
                .isEqualTo(HttpStatus.OK);
        assertThat(userService.getAll()).extracting(u -> u.getName())
                .containsExactly("different name");

    }

    @Test
    @DirtiesContext
    public void test_create_twice_the_same() throws Exception {

        // given

        CreateUserJson json = createJson("test_create_twice_the_same");
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
        CreateUserJson json = createJson(UUID.fromString(
                "99999999-9999-9999-9999-999999999999"));

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
