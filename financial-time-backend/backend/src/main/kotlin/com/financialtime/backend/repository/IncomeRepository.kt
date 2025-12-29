package com.financialtime.backend.repository

import com.financialtime.backend.model.Income
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface IncomeRepository : JpaRepository<Income, Long> {
    @Query("SELECT i FROM Income i ORDER BY i.date DESC")
    fun findAllOrderByDateDesc(): List<Income>
}