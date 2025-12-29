package com.financialtime.backend.service

import com.financialtime.backend.model.Income
import com.financialtime.backend.repository.IncomeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IncomeService(private val incomeRepository: IncomeRepository) {
    
    fun getAllIncome(): List<Income> = incomeRepository.findAllOrderByDateDesc()
    
    fun getIncomeById(id: Long): Income? = incomeRepository.findById(id).orElse(null)
    
    @Transactional
    fun createIncome(income: Income): Income = incomeRepository.save(income)
    
    @Transactional
    fun deleteIncome(id: Long): Boolean {
        return if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id)
            true
        } else false
    }
}