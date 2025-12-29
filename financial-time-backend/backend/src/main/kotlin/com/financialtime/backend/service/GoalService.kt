package com.financialtime.backend.service

import com.financialtime.backend.model.FinancialGoal
import com.financialtime.backend.repository.FinancialGoalRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FinancialGoalService(private val goalRepository: FinancialGoalRepository) {
    
    fun getAllGoals(): List<FinancialGoal> = goalRepository.findAllOrderByCreatedAtDesc()
    
    fun getGoalById(id: Long): FinancialGoal? = goalRepository.findById(id).orElse(null)
    
    @Transactional
    fun createGoal(goal: FinancialGoal): FinancialGoal = goalRepository.save(goal)
    
    @Transactional
    fun updateGoal(id: Long, goal: FinancialGoal): FinancialGoal? {
        return if (goalRepository.existsById(id)) {
            goalRepository.save(goal.copy(id = id))
        } else null
    }
    
    @Transactional
    fun updateGoalProgress(id: Long, progress: Int): FinancialGoal? {
        val goal = goalRepository.findById(id).orElse(null) ?: return null
        return goalRepository.save(goal.copy(progress = progress))
    }
    
    @Transactional
    fun deleteGoal(id: Long): Boolean {
        return if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id)
            true
        } else false
    }
}