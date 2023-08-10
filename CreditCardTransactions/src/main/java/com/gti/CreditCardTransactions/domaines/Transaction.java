package com.gti.CreditCardTransactions.domaines;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	 private int id;
	    private double amount;
	    private String merchant;
	    private String status;
	    private Date date;
}
