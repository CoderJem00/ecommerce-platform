package com.financialtime.backend.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "expenses")
data class Expense(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val description: String,
    
    @Column(nullable = false)
    val amount: BigDecimal,
    
    @Column(nullable = false)
    val category: String,
    
    @Column(nullable = false)
    val date: LocalDate,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)