package com.jasonleetoronto.capstone.springcassandrapaymentservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.exception.TransactionNotFoundException;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.Transaction;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.model.TransactionKey;
import com.jasonleetoronto.capstone.springcassandrapaymentservice.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class TransactionController {

    @Autowired
    TransactionServiceImpl transactionService;

    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> save(@Valid @RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        TransactionKey transactionKey = savedTransaction.getTransactionKey();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(convertTransactionKeyToStringId(transactionKey))
                .toUri();
        return ResponseEntity.created(location).build(); // Return the Http 201 Status Good Response with the newly created URI
    }

    @GetMapping(path = "/transactions/{composite_id}")
    public ResponseEntity<Object> findById(@PathVariable String composite_id) {

        TransactionKey transactionId = convertStringIdToTransactionKey(composite_id);
        Transaction transaction = transactionService.findByTransactionId(transactionId);

        /* Create a dynamic filter encapsulating ID, Chain, Date, Amount*/
        FilterProvider filter = createDynamicFilter(new String[]{"id", "chain", "date", "purchaseAmount"}, "TransactionFilter");
        MappingJacksonValue mapping = new MappingJacksonValue(transaction);
        mapping.setFilters(filter);

        return ResponseEntity.ok(mapping); // Return the Http 200 Status Good Response with the valid filtered transaction details
    }

    @DeleteMapping(path = "/transactions/{composite_id")
    public void deleteById(@PathVariable String composite_id) {
        TransactionKey transactionId = convertStringIdToTransactionKey(composite_id);
        transactionService.deleteByTransactionId(transactionId);
    }

    private FilterProvider createDynamicFilter(String[] fields, String filterName) {
        /* Define a filter */
        Set<String> props = new HashSet<>();
        for (String str : fields) {
            props.add(str);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(props);

        /*Configure a filter provider with a specific filter */
        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
        return filters;
    }

    private TransactionKey convertStringIdToTransactionKey(String composite_id) {
        String[] compositeKeyArray = composite_id.split("-");
        final String id = compositeKeyArray[0];
        final String chain = compositeKeyArray[1];
        final LocalDate date = LocalDate.parse(compositeKeyArray[2]);
        TransactionKey transactionId = new TransactionKey(id, chain, date);
        return transactionId;
    }

    private String convertTransactionKeyToStringId(TransactionKey transactionId) {
        return transactionId.getId() + "-" + transactionId.getChain() + "-" + transactionId.getDate();
    }
}
