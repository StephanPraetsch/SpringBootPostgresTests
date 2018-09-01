package com.mercateo.spring.boot.postgres.tests.users;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.mercateo.common.rest.schemagen.types.HyperSchemaCreator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class UrlSchemaCreator {

    @NonNull
    private final HyperSchemaCreator hyperSchemaCreator;

    ObjectWithSchema<Void> create() {
        return hyperSchemaCreator.create(null, Collections.emptyList());
    }

}
