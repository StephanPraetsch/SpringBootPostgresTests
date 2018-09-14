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
import com.mercateo.spring.boot.postgres.tests.users.UserId;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@EmbeddedPostgresJpaTest
public class UsersServiceDataTest {

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
        return new UserDto(userId.getValue(), name, birth);
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
