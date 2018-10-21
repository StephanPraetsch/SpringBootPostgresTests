package com.mercateo.spring.boot.postgres.tests.users.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import com.mercateo.spring.boot.postgres.tests.users.UserId;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@Ignore("https://github.com/testcontainers/testcontainers-java/issues/712")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(initializers = { UsersServiceDataTest.Initializer.class })
public class UsersServiceDataTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer =

            (PostgreSQLContainer) new PostgreSQLContainer("postgres:10.4")

                    .withDatabaseName("sampledb")

                    .withUsername("sampleuser")

                    .withPassword("samplepwd")

                    .withStartupTimeout(Duration.ofSeconds(600));

    static class Initializer

            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of(

                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),

                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),

                    "spring.datasource.password=" + postgreSQLContainer.getPassword()

            ).applyTo(configurableApplicationContext.getEnvironment());

        }

    }

    private UsersService uut;

    @Autowired
    private UsersRepo users;

    @Before
    public void init() {
        initMocks(this);
        uut = new UsersService(users);
    }

    private UserId userId(int i) {
        return new UserId(UUID.fromString(i + "-" + i + "-" + i + "-" + i + "-" + i));
    }

    private UserDto userDto(UserId userId, String name) {
        OffsetDateTime birth = OffsetDateTime.of(2018, 9, 13, 17, 15, 33, 0, ZoneOffset.UTC);
        return new UserDto(userId.getId(), name, birth);
    }

    @Test
    public void testGetAll_no_users() throws Exception {

        // given

        // when
        List<UserDto> result = uut.getAll();

        // then
        assertThat(result).isEmpty();

    }

    @Test
    public void testGetAll() throws Exception {

        // given
        UserId id = userId(1);
        UserDto userDto = userDto(id, "getAll");
        users.save(userDto);

        // when
        List<UserDto> result = uut.getAll();

        // then
        assertThat(result).containsExactly(userDto);

    }

    @Test
    public void testGetFor_not_found() throws Exception {

        // given
        UserId id = userId(2);

        // when
        Optional<UserDto> result = uut.getFor(id);

        // then
        assertThat(result).isEmpty();

    }

    @Test
    public void testGetFor() throws Exception {

        // given
        UserId id = userId(1);
        UserDto userDto = userDto(id, "getFor");
        users.save(userDto);

        // when
        Optional<UserDto> result = uut.getFor(id);

        // then
        assertThat(result).contains(userDto);

    }

    @Test
    public void testUpdate() throws Exception {

        // given
        UserId id = userId(1);
        UserDto userDto = userDto(id, "update");
        users.save(userDto);
        userDto.setName("new name");

        // when
        uut.update(userDto.toUser());

        // then
        assertThat(users.findById(userDto.getId())).contains(userDto);

    }

    @Test
    public void testCreate() throws Exception {

        // given
        UserId id = userId(1);
        UserDto userDto = userDto(id, "create");

        // when
        uut.create(userDto.toUser());

        // then
        assertThat(users.findById(userDto.getId())).contains(userDto);

    }

}
