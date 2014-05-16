package com.alchemytec;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alchemytec.dto.ExpenseDTO;
import com.alchemytec.model.Expense;
import com.alchemytec.services.ExpenseService;

/**
 * Tests for Expenses.
 * 
 * @author frangz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={TestApplication.class})
public class ExpenseServiceTest {

	@Autowired
	ExpenseService expenseService;

	/**
	 * Tests that the VAT is being saved correctly.
	 * 
	 * @throws Exception
	 */
	@Test
	public void vatTest() throws Exception {
		ExpenseDTO dto = new ExpenseDTO();
		Date date = new Date();
		dto.setDate(date);
		dto.setReason("reason");
		dto.setAmount(100.0);
		Expense expense = expenseService.save(dto);
		assertNotNull(expense);
		assertNotNull(expense.getId());
		assertEquals(expense.getAmount(), dto.getAmount(), 0);
		assertEquals(expense.getReason(), dto.getReason());
		assertEquals(expense.getVat(), 20.0, 0);
	}

}
