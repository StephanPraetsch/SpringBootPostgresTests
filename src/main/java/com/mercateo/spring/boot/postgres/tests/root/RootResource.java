package com.mercateo.spring.boot.postgres.tests.root;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;

import lombok.NonNull;

@Path("/")
public class RootResource implements JerseyResource {

    private final RootSchemaCreator rootSchemaCreator;

    @Inject
    public RootResource(@NonNull RootSchemaCreator rootSchemaCreator) {
        this.rootSchemaCreator = rootSchemaCreator;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ObjectWithSchema<Void> getRoot() {
        return rootSchemaCreator.create();

    }
}