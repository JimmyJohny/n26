package com.n26.challenge.model;

import java.time.Instant;

import net.sf.oval.constraint.ValidateWithMethod;

/**
 * @author jimmy
 *
 */

public class Transaction {
	
	private Double amount = 0.0;
	
	@ValidateWithMethod(methodName="validateTime", parameterType=Long.class)
	private Long timestamp = 0L;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	private boolean validateTime(Long timestamp) {
		
			return (Instant.now().toEpochMilli() - Instant.ofEpochMilli(timestamp).toEpochMilli() < 60000) ;
				
	}
	
	
}
