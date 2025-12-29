package com.financialtime.backend.controller

import com.financialtime.backend.model.Income
import com.financialtime.backend.service.IncomeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = ["http://localhost:3000", "http://192.168.0.10:3000"])
class IncomeController(private val incomeService: IncomeService) {
    
    @GetMapping
    fun getAllIncome(): ResponseEntity<List<Income>> {
        return ResponseEntity.ok(incomeService.getAllIncome())
    }
    
    @GetMapping("/{id}")
    fun getIncomeById(@PathVariable id: Long): ResponseEntity<Income> {
        val income = incomeService.getIncomeById(id)
        return if (income != null) {
            ResponseEntity.ok(income)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping
    fun createIncome(@RequestBody income: Income): ResponseEntity<Income> {
        val created = incomeService.createIncome(income)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }
    
    @DeleteMapping("/{id}")
    fun deleteIncome(@PathVariable id: Long): ResponseEntity<Void> {
        return if (incomeService.deleteIncome(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}