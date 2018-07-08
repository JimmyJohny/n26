/**
 * 
 */
package com.n26.challenge.model;

/**
 * @author jimmy
 *
 */
public class Statistics {

	 private double sum= 0;
	 private double avg =0;
	 private double max =0;
	 private double min =0;
	 private int count =0;
	
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
