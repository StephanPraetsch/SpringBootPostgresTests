package com.mercateo.spring.boot.postgres.tests.users.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

    @NonNull
    private final UsersRepo users;

    public List<UserDto> getAll() {
        return Lists.newArrayList(users.findAll());
    }

    public Optional<UserDto> getFor(@NonNull UserId id) {
        return users.findById(id.getValue());
    }

    public void update(@NonNull User user) {
        users.save(UserDto.fromUser(user));
    }

    public void create(@NonNull User user) {
        update(user);
    }

}
