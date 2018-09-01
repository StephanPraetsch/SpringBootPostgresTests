package com.mercateo.spring.boot.postgres.tests.users;

import java.time.OffsetDateTime;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class User {

    @NonNull
    UserId id;

    @NonNull
    String name;

    @Nullable
    OffsetDateTime birth;

}
