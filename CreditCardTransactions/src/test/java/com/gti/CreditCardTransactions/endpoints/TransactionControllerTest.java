package com.gti.CreditCardTransactions.endpoints;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.gti.CreditCardTransactions.domaines.Transaction;
import com.gti.CreditCardTransactions.domaines.TransactionFilter;
import com.gti.CreditCardTransactions.services.TransactionService;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private TransactionFilter filter;

    @BeforeEach
    public void setUp() {
        filter = new TransactionFilter();
        filter.setAmount(200.0);
        filter.setMerchant("Online Retailer");
        filter.setStatus("refused");
    }

    @Test
    public void testGetTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        Mockito.when(transactionService.TransactionFilter(filter)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }
    
    // test transactions without performing sorting or pagination.
    @Test
    public void testGetTransactionsWithoutSortingAndPagination() throws Exception {
        // Create test transactions
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, 100.0, "Merchant A", "Approved", new Date(0));
        Transaction transaction2 = new Transaction(2, 150.0, "Merchant B", "Pending", new Date(0));
        transactions.add(transaction1);
        transactions.add(transaction2);

        Mockito.when(transactionService.TransactionFilter(Mockito.any())).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100.0))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].merchant").value("Merchant A"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("Approved"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value(150.0))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].merchant").value("Merchant B"))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("Pending"));
    }
    
    // test request with pagination parameters page=2 and pageSize=3 to retrieve the second page with 3 transactions per page.
    // Finally, we use JSONPath assertions to verify that the response contains the expected transactions for page 2.
    @Test
    public void testPagination() throws Exception {
        // Create test transactions
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            transactions.add(new Transaction(i, i * 100.0, "Merchant " + i, "Status " + i, new Date(0)));
        }

        Mockito.when(transactionService.TransactionFilter(Mockito.any())).thenReturn(transactions);

        // Perform the request with pagination and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
               .param("page", "2")
               .param("pageSize", "3"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(4))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(5))
               .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(6));
    }
    
    //  Verify if the controller correctly returns filtered transactions based on the filtering criteria.
    @Test
    public void testFiltering() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 100.0, "Merchant A", "Approved", new Date(0)));
        transactions.add(new Transaction(2, 150.0, "Merchant B", "Pending", new Date(0)));

        Mockito.when(transactionService.TransactionFilter(Mockito.any()))
               .thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions")
               .content("{\"amount\": 100.0, \"merchant\": \"Merchant A\", \"status\": \"Approved\"}")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
}