package com.mercateo.spring.boot.postgres.tests.users.rest;

import java.time.OffsetDateTime;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercateo.rest.jersey.utils.listing.IdProvider;
import com.mercateo.spring.boot.postgres.tests.users.User;
import com.mercateo.spring.boot.postgres.tests.users.UserId;

import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
class UpdateUserJson implements IdProvider<UserId> {

    @NotNull
    private final UserId id;

    @NotNull
    private final String name;

    @Nullable
    private final OffsetDateTime birth;

    User toUser() {
        return new User(id, name, birth);
    }

}
