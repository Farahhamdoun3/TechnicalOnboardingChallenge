package com.gti.CreditCardTransactions.endpoints;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gti.CreditCardTransactions.domaines.Transaction;
import com.gti.CreditCardTransactions.domaines.TransactionFilter;
import com.gti.CreditCardTransactions.services.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    //  list of transactions based on filters, pagination, and sorting options.
    @PostMapping("/transactions")
    public List<Transaction> getTransactions(
            @RequestBody(required = false) TransactionFilter transactionFilter, 
            @RequestParam(defaultValue = "1") int page, 
            @RequestParam(defaultValue = "27") int pageSize, 
            @RequestParam(defaultValue = "false") Boolean sortByAmount, 
            @RequestParam(defaultValue = "false") Boolean sortByMerchant, 
            @RequestParam(defaultValue = "false") Boolean sortByStatus 
    ) throws IOException {
        //  list of transactions filtered
        List<Transaction> transactions = transactionService.TransactionFilter(transactionFilter);
        
        List<Transaction> transactionspagination;
        // Handle pagination of transactions

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, transactions.size());
        
        if (startIndex > transactions.size()) {
            transactionspagination = transactions;
        } else if (endIndex > transactions.size()) {
            transactionspagination = transactions.subList(startIndex, transactions.size());
        } else {
            transactionspagination = transactions.subList(startIndex, endIndex);
        }
        
        // Apply sorting by amount
        if (sortByAmount.equals(true)) {
            transactionspagination = transactionService.getTransactionsSortedByAmount(transactionspagination);
        }
        
        // Apply sorting by merchant 
        if (sortByMerchant.equals(true)) {
            transactionspagination = transactionService.getTransactionsSortedByMerchant(transactionspagination);
        }
        
        // Apply sorting by status 
        if (sortByStatus.equals(true)) {
            transactionspagination = transactionService.getTransactionsSortByStatus(transactionspagination);
        }
        
        return transactionspagination;
    }
}
