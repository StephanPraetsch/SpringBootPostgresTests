package com.mercateo.spring.boot.postgres.tests;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;

@Configuration
public class JpaTestConfiguration {

    @Bean
    public DataSource dataSource() throws Exception {
        configureJacksonForHibernate();
        return EmbeddedPostgres.builder().start().getTemplateDatabase();
    }

    private void configureJacksonForHibernate() {
        JacksonUtil.OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

}
