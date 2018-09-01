package com.mercateo.spring.boot.postgres.tests.users;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercateo.rest.jersey.utils.listing.IdProvider;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserJson implements IdProvider<UserId> {

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
