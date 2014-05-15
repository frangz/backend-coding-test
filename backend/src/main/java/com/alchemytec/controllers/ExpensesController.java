package com.alchemytec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alchemytec.model.Expense;
import com.alchemytec.persistence.ExpenseRepository;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {
	
	@Autowired
    protected ExpenseRepository expenseRepository;
	
    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String saveExpense(@RequestBody Expense e) {
		expenseRepository.save(e);    	
		return "OK";
    }

}
