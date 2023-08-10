package com.gti.CreditCardTransactions.services;

import java.io.IOException;
import java.util.List;

import com.gti.CreditCardTransactions.domaines.Transaction;
import com.gti.CreditCardTransactions.domaines.TransactionFilter;

public interface TransactionService {

	  public List<Transaction> TransactionFilter(TransactionFilter transactionFilter) throws IOException;

	  public List<Transaction> getTransactionsSortedByAmount(List<Transaction> transactions);

	  public List<Transaction> getTransactionsSortedByMerchant(List<Transaction> transactions);

	  public List<Transaction> getTransactionsSortByStatus(List<Transaction> transactionspagination);
}
