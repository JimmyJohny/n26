package com.n26.challenge.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.challenge.model.Statistics;
import com.n26.challenge.model.Transaction;
import com.n26.challenge.service.TransactionService;

/**
 * @author jimmy
 *
 */

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transactions")
	public ResponseEntity addTransactionService(@RequestBody Transaction transaction) {

		HttpStatus httpStatus =transactionService.addTxn(transaction);
		return new ResponseEntity(httpStatus);
	}

	@GetMapping("/statistics")
	public ResponseEntity<Statistics> getStatistics() {
		
		return new ResponseEntity<Statistics>(transactionService.getStatistics(), HttpStatus.OK);
	}

}
