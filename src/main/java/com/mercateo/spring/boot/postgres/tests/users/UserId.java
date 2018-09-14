package com.mercateo.spring.boot.postgres.tests.users;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value
public class UserId {

    @NonNull
    UUID id;

    public static UserId fromString(@NonNull String value) {
        return new UserId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
