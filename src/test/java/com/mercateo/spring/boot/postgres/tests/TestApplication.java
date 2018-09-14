package com.mercateo.spring.boot.postgres.tests;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootApplication( //
        exclude = {}, //
        scanBasePackages = { "com.mercateo.spring.boot.postgres.tests.*" })
@Import(value = { ApplicationConfiguration.class, JerseyConfiguration.class })
public interface TestApplication {

}
