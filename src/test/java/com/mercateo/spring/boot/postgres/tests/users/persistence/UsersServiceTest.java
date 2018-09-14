package com.mercateo.spring.boot.postgres.tests.users.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.google.common.testing.NullPointerTester;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

public class UsersServiceTest {

    private UsersService uut;

    @Mock
    private UsersRepo users;

    @Mock
    private UserDto userDto;

    private final UserId id = new UserId(UUID.fromString("1-1-1-1-1"));

    private final OffsetDateTime birth = OffsetDateTime.of(2018, 9, 13, 17, 15, 33, 0,
            ZoneOffset.UTC);

    private final User user = new User(id, "name", birth);

    @Before
    public void init() {
        initMocks(this);
        uut = new UsersService(users);
    }

    @Test
    public void testNullContracts() {
        NullPointerTester tester = new NullPointerTester();
        tester.testAllPublicInstanceMethods(uut);
        tester.testAllPublicConstructors(uut.getClass());
        tester.testAllPublicStaticMethods(uut.getClass());
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
        when(users.findAll()).thenReturn(Arrays.asList(userDto));

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
        when(users.findById(id.getId())).thenReturn(Optional.of(userDto));

        // when
        Optional<UserDto> result = uut.getFor(id);

        // then
        assertThat(result).contains(userDto);

    }

    @Test
    public void testUpdate() throws Exception {

        // given
        UserDto expected = UserDto.fromUser(user);

        // when
        uut.update(user);

        // then
        verify(users).save(expected);

    }

    @Test
    public void testCreate() throws Exception {

        // given
        UserDto expected = UserDto.fromUser(user);

        // when
        uut.create(user);

        // then
        verify(users).save(expected);

    }

}
