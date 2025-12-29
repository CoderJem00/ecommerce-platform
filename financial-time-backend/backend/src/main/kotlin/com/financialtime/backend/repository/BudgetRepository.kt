package com.financialtime.backend.repository

import com.financialtime.backend.model.Budget
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BudgetRepository : JpaRepository<Budget, Long> {
    fun findByCategory(category: String): Budget?
    
    fun findByPeriod(period: String): List<Budget>
}