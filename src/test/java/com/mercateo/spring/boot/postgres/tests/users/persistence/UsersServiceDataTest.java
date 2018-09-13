package com.mercateo.spring.boot.postgres.tests.users.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mercateo.spring.boot.postgres.tests.EmbeddedPostgresJpaTest;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@EmbeddedPostgresJpaTest
public class UsersServiceDataTest {

    private UsersService uut;

    @Autowired
    private UsersRepo users;

    private final UserId id = new UserId(UUID.fromString("1-1-1-1-1"));

    private final OffsetDateTime birth = OffsetDateTime.of(2018, 9, 13, 17, 15, 33, 0,
            ZoneOffset.UTC);

    private final User user = new User(id, "name", birth);

    @Before
    public void init() {
        initMocks(this);
        uut = new UsersService(users);
    }

    private UserDto userDto(int i, String name) {
        return new UserDto(UUID.fromString(i + "-" + i + "-" + i + "-" + i + "-" + i), name, birth);
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
        UserDto userDto = userDto(1, "getAll");
        users.save(userDto);

        // when
        List<UserDto> result = uut.getAll();

        // then
        assertThat(result).containsExactly(userDto);

    }

    @Test
    public void testGetFor_not_found() throws Exception {

        // given

        // when
        Optional<UserDto> result = uut.getFor(id);

        // then
        assertThat(result).isEmpty();

    }

    @Test
    public void testGetFor() throws Exception {

        // given
        UserDto userDto = userDto(2, "getFor");
        users.save(userDto);

        // when
        Optional<UserDto> result = uut.getFor(id);

        // then
        assertThat(result).contains(userDto);

    }

    @Test
    public void testUpdate() throws Exception {

        // given
        UserDto userDto = userDto(3, "update");
        users.save(userDto);
        userDto.setName("new name");

        // when
        uut.update(user);

        // then
        assertThat(users.findById(userDto.getId())).isEqualTo(userDto);

    }

    @Test
    public void testCreate() throws Exception {

        // given
        UserDto userDto = userDto(4, "create");

        // when
        uut.create(user);

        // then
        assertThat(users.findById(userDto.getId())).isEqualTo(userDto);

    }

}
