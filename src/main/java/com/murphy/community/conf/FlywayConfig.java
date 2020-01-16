package com.murphy.community.conf;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * FlywayConfig
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/1/16 1:29 下午
 */

@Configuration
public class FlywayConfig {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
    }
}
