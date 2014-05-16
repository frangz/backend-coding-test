package com.alchemytec.dto;

import java.util.Date;

/**
 * Data transfer object for expenses. DTOs are typically used to transfer data
 * between the user and the presentation layer. They help us restricting and
 * formatting the information the user sees.
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
