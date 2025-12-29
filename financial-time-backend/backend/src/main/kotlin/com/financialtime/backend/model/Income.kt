package com.financialtime.backend.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "income")
data class Income(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val source: String,
    
    @Column(nullable = false)
    val amount: BigDecimal,
    
    @Column(nullable = false)
    val date: LocalDate,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)