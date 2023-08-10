package com.gti.CreditCardTransactions.services;


import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gti.CreditCardTransactions.Repository.TransactionRepository;
import com.gti.CreditCardTransactions.domaines.Transaction;
import com.gti.CreditCardTransactions.domaines.TransactionFilter;


@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Override
	public List<Transaction> TransactionFilter(TransactionFilter transactionFilter) throws IOException {
     return transactionRepository.FilterTransactions(transactionFilter);
	}
	@Override
	 public List<Transaction> getTransactionsSortedByAmount(List<Transaction> transactions) {
         return transactions.stream().sorted(Comparator.comparing(Transaction::getAmount))
         .collect(Collectors.toList());
	  }
	@Override
	 public List<Transaction> getTransactionsSortedByMerchant(List<Transaction> transactions) {
        return transactions.stream().sorted(Comparator.comparing(Transaction::getMerchant))
        .collect(Collectors.toList());
	  }
	@Override
	public List<Transaction> getTransactionsSortByStatus(List<Transaction> transactions) {
		 return transactions.stream().sorted(Comparator.comparing(Transaction::getStatus))
			        .collect(Collectors.toList());
	}
}
