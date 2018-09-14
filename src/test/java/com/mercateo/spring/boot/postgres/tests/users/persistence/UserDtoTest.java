package com.mercateo.spring.boot.postgres.tests.users.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.junit.Test;

import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

public class UserDtoTest {

    private final UserId id = new UserId(UUID.fromString("1-1-1-1-1"));

    private final String name = "the name";

    private final OffsetDateTime odt = OffsetDateTime.of(2018, 9, 13, 17, 5, 35, 0, ZoneOffset.UTC);

    @Test
    public void testFromUser() throws Exception {

        // given
        User user = new User(id, name, odt);

        // when
        UserDto result = UserDto.fromUser(user);

        // then
        assertThat(result.getId()).isEqualTo(id.getId());
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getBirth()).isEqualTo(odt);

    }

}
