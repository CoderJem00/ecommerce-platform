package com.financialtime.backend.controller

import com.financialtime.backend.model.FinancialGoal
import com.financialtime.backend.service.FinancialGoalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = ["http://localhost:3000", "http://192.168.0.10:3000"])
class FinancialGoalController(private val goalService: FinancialGoalService) {
    
    @GetMapping
    fun getAllGoals(): ResponseEntity<List<FinancialGoal>> {
        return ResponseEntity.ok(goalService.getAllGoals())
    }
    
    @GetMapping("/{id}")
    fun getGoalById(@PathVariable id: Long): ResponseEntity<FinancialGoal> {
        val goal = goalService.getGoalById(id)
        return if (goal != null) {
            ResponseEntity.ok(goal)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping
    fun createGoal(@RequestBody goal: FinancialGoal): ResponseEntity<FinancialGoal> {
        val created = goalService.createGoal(goal)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }
    
    @PutMapping("/{id}")
    fun updateGoal(
        @PathVariable id: Long,
        @RequestBody goal: FinancialGoal
    ): ResponseEntity<FinancialGoal> {
        val updated = goalService.updateGoal(id, goal)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PatchMapping("/{id}/progress")
    fun updateGoalProgress(
        @PathVariable id: Long,
        @RequestParam progress: Int
    ): ResponseEntity<FinancialGoal> {
        val updated = goalService.updateGoalProgress(id, progress)
        return if (updated != null) {
            ResponseEntity.ok(updated)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteGoal(@PathVariable id: Long): ResponseEntity<Void> {
        return if (goalService.deleteGoal(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}