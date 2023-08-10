package com.gti.CreditCardTransactions.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gti.CreditCardTransactions.domaines.Transaction;
import com.gti.CreditCardTransactions.domaines.TransactionFilter;
import com.gti.CreditCardTransactions.exception.BadRequestParmsException;

import jakarta.annotation.PostConstruct;
@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

	private  List<Transaction> transactions;
	@Override
	public List<Transaction> fetchData() throws IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        ClassPathResource resource = new ClassPathResource("transactionsMock.json");
	        Transaction[] transactionsArray = objectMapper.readValue(resource.getInputStream(), Transaction[].class);
	        return  Arrays.asList(transactionsArray);
	}
	@Override
	public List<Transaction> FilterTransactions(TransactionFilter transactionFilter) throws IOException {
		List<Transaction>  transactionsFiltered=fetchData();

		if(transactionFilter != null) {
			if (transactionFilter.getMerchant() != null) {
				
				transactionsFiltered = transactionsFiltered.stream()
	                    .filter(transaction -> transaction.getMerchant().equals(transactionFilter.getMerchant()))
	                    .collect(Collectors.toList());
	        }
	        if (transactionFilter.getStatus() != null) {
	        	if (!transactionFilter.getStatus().equals("approved") && !transactionFilter.getStatus().equals("refused")  ) {
	        	    throw new BadRequestParmsException("status  must be  approved or refused ");

	        	}
	        	transactionsFiltered = transactionsFiltered.stream()
	                    .filter(transaction -> transaction.getStatus().equals(transactionFilter.getStatus()))
	                    .collect(Collectors.toList());
	        }
	        if (transactionFilter.getAmount() != 0) {
	        	if (transactionFilter.getAmount() <= 0) {
	        	    throw new BadRequestParmsException("Amount cannot be negative.");
	        	}
	        	transactionsFiltered = transactionsFiltered.stream()
	                    .filter(transaction -> transaction.getAmount()==transactionFilter.getAmount())
	                    .collect(Collectors.toList());
	        }
		}
    
        return transactionsFiltered;
        

        
	}
}
