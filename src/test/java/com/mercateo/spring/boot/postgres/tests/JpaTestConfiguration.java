package com.mercateo.spring.boot.postgres.tests;

import java.io.IOException;

import org.postgresql.Driver;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;

import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

@Configuration
@ImportAutoConfiguration({ DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
        TransactionAutoConfiguration.class })
public class JpaTestConfiguration {

    // static org.apache.tomcat.jdbc.pool.DataSource ds;

    static {

        try {

            configureJacksonForHibernate();

            PostgresConfig pgConfig = PostgresConfig.defaultWithDbName("embedded", "test",
                    "test");
            PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter
                    .getDefaultInstance();
            PostgresExecutable exec = runtime.prepare(pgConfig);
            exec.start();

            String host = pgConfig.net().host();
            int port = pgConfig.net().port();
            String dbName = pgConfig.storage().dbName();
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
            //

            System.setProperty("spring.datasource.driver-class-name", Driver.class.getName());

            System.setProperty("spring.datasource.username", pgConfig.credentials().username());
            System.setProperty("spring.datasource.password", pgConfig.credentials().password());
            System.setProperty("spring.datasource.host", host);
            System.setProperty("spring.datasource.port", Integer.valueOf(port).toString());

            System.setProperty("spring.datasource.url", url);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static void configureJacksonForHibernate() {
        JacksonUtil.OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

}
