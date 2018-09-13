package com.mercateo.spring.boot.postgres.tests.users.rest;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercateo.rest.jersey.utils.listing.IdProvider;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class UpdateUserJson implements IdProvider<UserId> {

    @NotNull
    private final UserId id;

    @NotNull
    private final String name;

    @NotNull
    private final OffsetDateTime birth;

    User toUser() {
        return new User(id, name, birth);
    }

}
