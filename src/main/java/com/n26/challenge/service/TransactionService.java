/**
 * 
 */
package com.n26.challenge.service;

import org.springframework.http.HttpStatus;

import com.n26.challenge.model.Statistics;
import com.n26.challenge.model.Transaction;

/**
 * @author jimmy
 *
 */
public interface TransactionService {

	public HttpStatus addTxn( Transaction transaction );
	public Statistics getStatistics() ;
}
