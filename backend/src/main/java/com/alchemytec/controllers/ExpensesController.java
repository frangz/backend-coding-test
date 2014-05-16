package com.alchemytec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alchemytec.dto.ExpenseDTO;
import com.alchemytec.model.Expense;
import com.alchemytec.services.ExpenseService;

/**
 * Controllers serve web request and are usually the entry point to the presentation layer. 
 * 
 * This is a REST controller using Spring MVC. If we didn't have Spring in our application and we
 * wanted to keep it lightweight, we could use many other frameworks that do REST, such as Jersey or RestEasy.
 * 
 * @author frangz
 */
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
    @ResponseStatus(HttpStatus.CREATED)
    public String saveExpense(@RequestBody ExpenseDTO e) {
		expenseService.save(e);    	
		return "OK";
    }

}
