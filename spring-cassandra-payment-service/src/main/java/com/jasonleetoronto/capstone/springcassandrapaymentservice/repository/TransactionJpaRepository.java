package com.jasonleetoronto.capstone.springcassandrapaymentservice.repository;

import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.Transaction;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.TransactionKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

/* Using Cassandra Spring Data JPA,
   define Transaction JPA Repository Interface extending CassandraRepository<Table, Key>
   to manage Transaction Entities
 */
public interface TransactionJpaRepository extends CassandraRepository<Transaction, TransactionKey> {

}
