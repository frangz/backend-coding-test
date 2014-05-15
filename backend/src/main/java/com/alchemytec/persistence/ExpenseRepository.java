package com.alchemytec.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alchemytec.model.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

}
