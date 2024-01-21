package com.example.expense_tracker.resources;

import com.example.expense_tracker.domain.Category;
import com.example.expense_tracker.domain.Transaction;
import com.example.expense_tracker.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/categories/{categoryId}/transactions")
public class TransactionResource {

    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId){
        int userId = (Integer) request.getAttribute("userId");
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId,categoryId);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId,
                                                      @RequestBody Map<String,Object> transactionMap){
        int userId = (Integer) request.getAttribute("userId");
        Double amount = Double.valueOf(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        Long transactionDate = (Long) transactionMap.get("transactionDate");
        Transaction transaction = transactionService.addTransaction(userId,categoryId,amount,note,transactionDate);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<Transaction> getCategoryById(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId
            ,@PathVariable("transactionId") Integer transactionId){
        int userId = (Integer) request.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransactionById(userId,categoryId,transactionId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @PutMapping("{transactionId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest request,
                                                              @PathVariable("categoryId") Integer categoryId ,
                                                              @PathVariable("transactionId") Integer transactionId ,
                                                              @RequestBody Transaction transaction){
        int userId = (Integer) request.getAttribute("userId");
        transactionService.updateTransaction(userId,categoryId,transactionId,transaction);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("{transactionId}")
    public ResponseEntity<Map<String,Boolean>> deleteCategory(HttpServletRequest request,
                                                              @PathVariable("categoryId") Integer categoryId ,
                                                              @PathVariable("transactionId") Integer transactionId){
        int userId = (Integer) request.getAttribute("userId");
        transactionService.removeTransaction(userId,categoryId,transactionId);
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
