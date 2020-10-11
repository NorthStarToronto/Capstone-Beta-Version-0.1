package com.jasonleetoronto.capstone.springcassandrapaymentservice.repository;

import com.jasonleetoronto.capstone.springcassandrapaymentservice.SpringCassandraPaymentServiceApplication;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.Transaction;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.TransactionKey;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCassandraPaymentServiceApplication.class)
class TransactionJpaRepositoryTest {

    private static Logger LOG = LoggerFactory.getLogger(TransactionJpaRepositoryTest.class);

    @Autowired
    TransactionJpaRepository transactionJpaRepository;

    @Test
    void insertTransaction() {
        Transaction transactionInserted = new Transaction(new TransactionKey("20202", "102", LocalDate.of(2020, 2, 20)), "8", "707", "1078778070", "12564", "12", "OZ", 1, 7.59F);
        transactionJpaRepository.save(transactionInserted);

        TransactionKey transactionKey = new TransactionKey("20202", "102", LocalDate.of(2020, 2, 20));
        Optional<Transaction> transactionQueried = transactionJpaRepository.findById(transactionKey);
        LOG.info(transactionQueried.orElse(null).toString());
    }

}
