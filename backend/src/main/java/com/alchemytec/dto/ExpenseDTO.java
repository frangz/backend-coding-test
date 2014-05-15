package com.alchemytec.dto;

import java.util.Date;

/**
 * Data transfer object for expenses. Used to receive data from the front end.
 * 
 * @author frangz
 */
public class ExpenseDTO {

	private Date date;
	private double amount;	
	private String reason;
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
