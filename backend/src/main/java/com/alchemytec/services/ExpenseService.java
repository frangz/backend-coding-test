package com.alchemytec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alchemytec.dto.ExpenseDTO;
import com.alchemytec.model.Expense;
import com.alchemytec.persistence.ExpenseRepository;

/**
 * This is the business layer, or the classes that have our business logic. This
 * classes have to be fully covered by tests. In our example, I'm considering
 * the VAT calculation as the only bit of business logic we need to apply.
 * 
 * @author frangz
 */
@Component
public class ExpenseService {

	// This should be configurable outside the app as it might change
	private static final double VAT = 0.2;

	@Autowired
	private ExpenseRepository expenseRepository;

	public Iterable<Expense> findAll() {
		return expenseRepository.findAll();
	}

	public Expense save(ExpenseDTO dto) {
		Expense exp = new Expense();
		exp.setAmount(dto.getAmount());
		exp.setDate(dto.getDate());
		exp.setVat(dto.getAmount() * VAT);
		exp.setReason(dto.getReason());

		// Validate expense.
		return expenseRepository.save(exp);
	}

}
