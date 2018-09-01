package com.mercateo.spring.boot.postgres.tests.users;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
class CreateUserJson {

    @NotNull
    private final UUID id;

    @NotNull
    private final String name;

    @Nullable
    private final OffsetDateTime birth;

    User toUser() {
        return new User(new UserId(id), name, birth);
    }

}
