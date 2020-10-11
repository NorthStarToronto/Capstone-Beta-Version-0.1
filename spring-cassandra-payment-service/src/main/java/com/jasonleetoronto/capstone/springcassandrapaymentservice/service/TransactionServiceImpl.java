package com.jasonleetoronto.capstone.springcassandrapaymentservice.service;

import com.jasonleetoronto.capstone.springcassandrapaymentservice.exception.TransactionNotFoundException;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.Transaction;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.TransactionKey;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.repository.TransactionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/* Create Transaction Service mapping Transaction Service to Transaction JPA Repository */
@Service
public class TransactionServiceImpl implements TransactionService {

    /* Null Pointer Exception Handled in the Service Level */

    @Autowired
    TransactionJpaRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public void deleteByTransactionId(TransactionKey transactionId) {

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

        /* Check for the Null Pointer Exception */
        if (!optionalTransaction.isPresent())
            throw new TransactionNotFoundException("ID - " + transactionId.toString());

        transactionRepository.deleteById(transactionId);
    }

    @Override
    public Transaction findByTransactionId(TransactionKey transactionId) {

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

        /* Check for the Null Pointer Exception */
        if (!optionalTransaction.isPresent())
            throw new TransactionNotFoundException("ID -" + transactionId.toString());

        /* Return the valid Transaction */
        return optionalTransaction.get();
    }
}
