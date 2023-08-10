package com.gti.CreditCardTransactions.Repository;

import java.io.IOException;
import java.util.List;

import com.gti.CreditCardTransactions.domaines.Transaction;
import com.gti.CreditCardTransactions.domaines.TransactionFilter;

public interface TransactionRepository {
	public List<Transaction> FilterTransactions(TransactionFilter transactionFilter) throws IOException;

	List<Transaction> fetchData() throws IOException;
}
