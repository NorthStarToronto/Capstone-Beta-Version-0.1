package com.jasonleetoronto.capstone.springcassandrapaymentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/* Configure Cassandra Data Source Settings and Enable SQL implementation by Cassandra Repository */
@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.data.cassandra.basepackage}")
    private String basePackages;

    @Override
    protected String getContactPoints() {
        return this.contactPoints;
    }

    @Override
    protected  int getPort() {
        return this.port;
    }

    @Override
    protected String getKeyspaceName() {
        return this.keySpace;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{this.basePackages};
    }
}
