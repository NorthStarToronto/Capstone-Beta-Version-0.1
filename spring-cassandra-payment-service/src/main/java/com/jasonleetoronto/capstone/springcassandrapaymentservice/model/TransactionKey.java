package com.jasonleetoronto.capstone.springcassandrapaymentservice.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;

/* Define Composite Primary Key Class and Map it to Cassandra Table Columns */
@PrimaryKeyClass
public class TransactionKey implements Serializable {

    /* Implement Serializable */
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "chain", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String chain;

    @PrimaryKeyColumn(name = "date", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDate date;

    /* Default No Argument Constructor */
    protected TransactionKey() {
    }

    public TransactionKey(String id, String chain, LocalDate date) {
        super();
        this.id = id;
        this.chain = chain;
        this.date = date;
    }

    /* Getters and Setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.id +  "-" + this.chain + "-" + this.date;
    }

    /* Equals Method */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TransactionKey)) return false;

        TransactionKey that = (TransactionKey) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getChain(), that.getChain())
                .append(getDate(), that.getDate())
                .isEquals();
    }

    /* HashCode Method */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getChain())
                .append(getDate())
                .toHashCode();
    }
}
