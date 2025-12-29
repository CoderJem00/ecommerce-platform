package com.financialtime.backend.repository

import com.financialtime.backend.model.FinancialGoal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FinancialGoalRepository : JpaRepository<FinancialGoal, Long> {
    @Query("SELECT g FROM FinancialGoal g ORDER BY g.createdAt DESC")
    fun findAllOrderByCreatedAtDesc(): List<FinancialGoal>
}