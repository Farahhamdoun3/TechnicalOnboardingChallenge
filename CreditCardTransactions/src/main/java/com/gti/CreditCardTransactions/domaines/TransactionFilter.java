package com.gti.CreditCardTransactions.domaines;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionFilter {
	private double amount;
    private String merchant;
    private String status;
}
