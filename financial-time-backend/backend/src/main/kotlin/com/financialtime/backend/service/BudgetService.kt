package com.financialtime.backend.service

import com.financialtime.backend.model.Budget
import com.financialtime.backend.repository.BudgetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BudgetService(private val budgetRepository: BudgetRepository) {
    
    fun getAllBudgets(): List<Budget> = budgetRepository.findAll()
    
    fun getBudgetById(id: Long): Budget? = budgetRepository.findById(id).orElse(null)
    
    fun getBudgetByCategory(category: String): Budget? = 
        budgetRepository.findByCategory(category)
    
    fun getBudgetsByPeriod(period: String): List<Budget> = 
        budgetRepository.findByPeriod(period)
    
    @Transactional
    fun createBudget(budget: Budget): Budget = budgetRepository.save(budget)
    
    @Transactional
    fun updateBudget(id: Long, budget: Budget): Budget? {
        return if (budgetRepository.existsById(id)) {
            budgetRepository.save(budget.copy(id = id))
        } else null
    }
    
    @Transactional
    fun deleteBudget(id: Long): Boolean {
        return if (budgetRepository.existsById(id)) {
            budgetRepository.deleteById(id)
            true
        } else false
    }
}