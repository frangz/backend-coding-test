package com.alchemytec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alchemytec.dto.ExpenseDTO;
import com.alchemytec.model.Expense;
import com.alchemytec.services.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {
	
	@Autowired
    protected ExpenseService expenseService;
	
    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Expense> getAllExpenses() {
        return expenseService.findAll();
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String saveExpense(@RequestBody ExpenseDTO e) {
		expenseService.save(e);    	
		return "OK";
    }

}
