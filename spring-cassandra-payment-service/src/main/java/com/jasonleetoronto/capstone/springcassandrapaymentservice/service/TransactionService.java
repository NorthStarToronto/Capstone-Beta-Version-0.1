package com.jasonleetoronto.capstone.springcassandrapaymentservice.service;

import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.Transaction;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.TransactionKey;

/* 3X Boiler Plate CRUD Operation Methods to implemented for the Transaction Service */
public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    void deleteByTransactionId(TransactionKey transactionId);

    Transaction findByTransactionId(TransactionKey transactionId);
}
