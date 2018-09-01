package com.mercateo.spring.boot.postgres.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.properties")
@Retention(RetentionPolicy.RUNTIME)
@Import(JpaTestConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public @interface EmbeddedPostgresForWebTest {

}
