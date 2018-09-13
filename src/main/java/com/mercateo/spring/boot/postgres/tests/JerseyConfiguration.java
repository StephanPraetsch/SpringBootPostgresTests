package com.mercateo.spring.boot.postgres.tests;

import java.util.Arrays;
import java.util.List;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.rest.jersey.utils.exception.RFCExceptionMapper;
import com.mercateo.spring.boot.postgres.tests.root.RootResource;
import com.mercateo.spring.boot.postgres.tests.users.rest.UsersResource;

@Configuration
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {

        property(ServerProperties.WADL_FEATURE_DISABLE, Boolean.TRUE);
        register(JacksonFeature.class);

        register(RFCExceptionMapper.class);
        // register(RoleRequestFilter.class); TODO add this here?!

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        resources().forEach(this::register);

    }

    private List<Class<? extends JerseyResource>> resources() {
        return Arrays.asList(RootResource.class, UsersResource.class);
    }

}
