package com.financialtime.backend.controller

import com.financialtime.backend.model.Budget
import com.financialtime.backend.service.BudgetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = ["http://localhost:3000", "http://192.168.0.10:3000"])
class BudgetController(private val budgetService: BudgetService) {
    
    @GetMapping
    fun getAllBudgets(): ResponseEntity<List<Budget>> {
        return ResponseEntity.ok(budgetService.getAllBudgets())
    }
    
    @GetMapping("/{id}")
    fun getBudgetById(@PathVariable id: Long): ResponseEntity<Budget> {
        val budget = budgetService.getBudgetById(id)
        return if (budget != null) {
            ResponseEntity.ok(budget)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/category/{category}")
    fun getBudgetByCategory(@PathVariable category: String): ResponseEntity<Budget> {
        val budget = budgetService.getBudgetByCategory(category)
        return if (budget != null) {
            ResponseEntity.ok(budget)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/period/{period}")
    fun getBudgetsByPeriod(@PathVariable period: String): ResponseEntity<List<Budget>> {
        return ResponseEntity.ok(budgetService.getBudgetsByPeriod(period))
    }
    
    @PostMapping
    fun createBudget(@RequestBody budget: Budget): ResponseEntity<Budget> {
        val created = budgetService.createBudget(budget)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }
    
    @PutMapping("/{id}")
    fun updateBudget(
        @PathVariable id: Long,
        @RequestBody budget: Budget
    ): ResponseEntity<Budget> {
        val updated = budgetService.updateBudget(id, budget)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteBudget(@PathVariable id: Long): ResponseEntity<Void> {
        return if (budgetService.deleteBudget(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}