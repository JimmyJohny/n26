/**
 * 
 */
package com.n26.challenge.service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;

import com.n26.challenge.model.Statistics;
import com.n26.challenge.model.Transaction;
import com.n26.challenge.util.Utils;


/**
 * @author jimmy
 *
 */

public class TransactionServiceImpl implements InitializingBean,TransactionService{

	private Statistics statistics =  new Statistics(); 
	private Statistics latestStatistics =  new Statistics(); 
	private AtomicInteger count = new AtomicInteger();
	private Long initTime  ;
	private List<Transaction> txnsList = new CopyOnWriteArrayList<Transaction>();
	private static volatile boolean scheduler=false;
	private ReadWriteLock lock = new ReentrantReadWriteLock();	


	public HttpStatus addTxn( Transaction transaction ) {

		lock.writeLock().lock();
		try {
			txnsList.add(transaction);
		}finally {
			lock.writeLock().unlock();
		}
		if(!scheduler) {
			statistics.setCount(count.incrementAndGet()); 
			calculate(transaction);
		}
		return HttpStatus.CREATED;

	}

	private void calculate(Transaction transaction) {
		statistics.setSum(Utils.round(statistics.getSum()+transaction.getAmount()));
		statistics.setAvg(Utils.round(statistics.getSum()/statistics.getCount()));
		statistics.setMax(statistics.getMax() > transaction.getAmount() ? statistics.getMax(): transaction.getAmount() );
		statistics.setMin(statistics.getMin() == 0.0 ? transaction.getAmount() : ((statistics.getMin() < transaction.getAmount()) ? statistics.getMin(): transaction.getAmount()));
	}

	public Statistics getStatistics() {

		if(!scheduler)
			return statistics;
		else return latestStatistics;

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Instant instant = Instant.now();
		initTime = instant.toEpochMilli();	

		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				scheduler=true;
				lock.writeLock().lock();
				try {
					initTime=initTime+1000;
					txnsList = txnsList.parallelStream().filter(e -> e.getTimestamp() >= initTime).collect(Collectors.toList());
					latestStatistics= new Statistics();	
					for(Transaction transaction : txnsList) {
						latestStatistics.setSum(Utils.round(latestStatistics.getSum()+transaction.getAmount()));
						latestStatistics.setMax(latestStatistics.getMax() > transaction.getAmount() ? latestStatistics.getMax(): transaction.getAmount() );
						latestStatistics.setMin(latestStatistics.getMin() == 0.0 ? transaction.getAmount() : ((latestStatistics.getMin() < transaction.getAmount()) ? latestStatistics.getMin(): transaction.getAmount()));
					}
					if(txnsList.size() >0) {
						latestStatistics.setCount(txnsList.size());
						latestStatistics.setAvg(Utils.round(latestStatistics.getSum()/latestStatistics.getCount()));
					}}finally {
						lock.writeLock().unlock();
					}

			}
		}, 60000, 1000, TimeUnit.MILLISECONDS);
	}

}
