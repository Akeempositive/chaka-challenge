package com.positive.chaka.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "transactions", method = RequestMethod.POST)
    public ResponseEntity saveTransaction(@RequestBody Map<String, Object> transaction){
        return transactionService.addToTransaction(transaction);
    }

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    public ResponseEntity<Statistics> getStatistics(){
        return transactionService.getStatistics();
    }

    @RequestMapping(value = "transactions", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAllTransactions(){
        return transactionService.deleteAllTransactions();
    }
}


