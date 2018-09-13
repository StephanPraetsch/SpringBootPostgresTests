package com.mercateo.spring.boot.postgres.tests.users.rest;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mercateo.rest.jersey.utils.listing.IdProvider;
import com.mercateo.spring.boot.postgres.tests.users.UserId;
import com.mercateo.spring.boot.postgres.tests.users.persistence.UserDto;

import lombok.NonNull;
import lombok.Value;

@Value
class UserJson implements IdProvider<UserId> {

    @NonNull
    @JsonUnwrapped
    private final UserId id;

    String name;

    OffsetDateTime birth;

    static UserJson from(@NonNull UserDto user) {
        return new UserJson(
                new UserId(user.getId()),
                user.getName(),
                user.getBirth());
    }

    @Override
    public UserId getId() {
        return id;
    }

}
