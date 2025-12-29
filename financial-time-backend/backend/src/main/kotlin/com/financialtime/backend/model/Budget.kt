package com.financialtime.backend.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "budgets")
data class Budget(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val category: String,
    
    @Column(nullable = false)
    val amount: BigDecimal,
    
    @Column(nullable = false)
    val period: String, // monthly, weekly, yearly
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)