package com.mercateo.spring.boot.postgres.tests.root;

import java.util.List;

import javax.inject.Named;
import javax.ws.rs.core.Link;

import com.mercateo.common.rest.schemagen.types.HyperSchemaCreator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.spring.boot.postgres.tests.users.UsersLinkCreator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class RootSchemaCreator {

    @NonNull
    private final HyperSchemaCreator hyperSchemaCreator;

    @NonNull
    private final UsersLinkCreator usersLinks;

    ObjectWithSchema<Void> create() {
        return hyperSchemaCreator.create(null, createLinks());
    }

    private List<Link> createLinks() {
        return usersLinks.getLinksToUsers();
    }

}
