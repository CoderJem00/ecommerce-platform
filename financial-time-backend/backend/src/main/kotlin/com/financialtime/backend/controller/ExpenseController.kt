package com.financialtime.backend.controller

import com.financialtime.backend.model.Expense
import com.financialtime.backend.service.ExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = ["http://localhost:3000", "http://192.168.0.10:3000"])
class ExpenseController(private val expenseService: ExpenseService) {
    
    @GetMapping
    fun getAllExpenses(): ResponseEntity<List<Expense>> {
        return ResponseEntity.ok(expenseService.getAllExpenses())
    }
    
    @GetMapping("/{id}")
    fun getExpenseById(@PathVariable id: Long): ResponseEntity<Expense> {
        val expense = expenseService.getExpenseById(id)
        return if (expense != null) {
            ResponseEntity.ok(expense)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/category/{category}")
    fun getExpensesByCategory(@PathVariable category: String): ResponseEntity<List<Expense>> {
        return ResponseEntity.ok(expenseService.getExpensesByCategory(category))
    }
    
    @GetMapping("/date-range")
    fun getExpensesByDateRange(
        @RequestParam startDate: String,
        @RequestParam endDate: String
    ): ResponseEntity<List<Expense>> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        return ResponseEntity.ok(expenseService.getExpensesByDateRange(start, end))
    }
    
    @PostMapping
    fun createExpense(@RequestBody expense: Expense): ResponseEntity<Expense> {
        val created = expenseService.createExpense(expense)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }
    
    @PutMapping("/{id}")
    fun updateExpense(
        @PathVariable id: Long,
        @RequestBody expense: Expense
    ): ResponseEntity<Expense> {
        val updated = expenseService.updateExpense(id, expense)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteExpense(@PathVariable id: Long): ResponseEntity<Void> {
        return if (expenseService.deleteExpense(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}