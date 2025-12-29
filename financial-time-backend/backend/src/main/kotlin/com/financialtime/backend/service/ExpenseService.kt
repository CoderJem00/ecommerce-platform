package com.financialtime.backend.service

import com.financialtime.backend.model.Expense
import com.financialtime.backend.repository.ExpenseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ExpenseService(private val expenseRepository: ExpenseRepository) {
    
    fun getAllExpenses(): List<Expense> = expenseRepository.findAllOrderByDateDesc()
    
    fun getExpenseById(id: Long): Expense? = expenseRepository.findById(id).orElse(null)
    
    fun getExpensesByCategory(category: String): List<Expense> = 
        expenseRepository.findByCategory(category)
    
    fun getExpensesByDateRange(startDate: LocalDate, endDate: LocalDate): List<Expense> = 
        expenseRepository.findByDateBetween(startDate, endDate)
    
    @Transactional
    fun createExpense(expense: Expense): Expense = expenseRepository.save(expense)
    
    @Transactional
    fun updateExpense(id: Long, expense: Expense): Expense? {
        return if (expenseRepository.existsById(id)) {
            expenseRepository.save(expense.copy(id = id))
        } else null
    }
    
    @Transactional
    fun deleteExpense(id: Long): Boolean {
        return if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id)
            true
        } else false
    }
}