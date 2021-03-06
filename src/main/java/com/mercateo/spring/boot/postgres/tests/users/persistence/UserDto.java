package com.mercateo.spring.boot.postgres.tests.users.persistence;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Id
    UUID id;

    String name;

    OffsetDateTime birth;

    public static UserDto fromUser(@NonNull User user) {
        return new UserDto(user.getId().getId(), user.getName(), user.getBirth());
    }

    public User toUser() {
        return new User(new UserId(id), name, birth);
    }

}
