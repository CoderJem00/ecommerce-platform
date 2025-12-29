package com.financialtime.backend.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "financial_goals")
data class FinancialGoal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val title: String,
    
    @Column(nullable = false)
    val targetAmount: BigDecimal,
    
    @Column
    val deadline: LocalDate? = null,
    
    @Column(nullable = false)
    val progress: Int = 0, // percentage 0-100
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)