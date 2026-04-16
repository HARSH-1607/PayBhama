package com.paybhama.app.utils

import org.junit.Assert.*
import org.junit.Test

class InputValidationTest {

    @Test
    fun testValidAmount() {
        // Valid amount with sufficient balance
        val result1 = InputValidation.isValidAmount("50.0", 100.0)
        assertTrue(result1.first)
        assertEquals("", result1.second)
    }

    @Test
    fun testInvalidFormatAmount() {
        // Not a number
        val result = InputValidation.isValidAmount("abc", 100.0)
        assertFalse(result.first)
        assertEquals("Invalid amount format", result.second)
    }

    @Test
    fun testNegativeAmount() {
        // Negative amount
        val result = InputValidation.isValidAmount("-10.0", 100.0)
        assertFalse(result.first)
        assertEquals("Amount must be greater than zero", result.second)
    }

    @Test
    fun testInsufficientBalance() {
        // Attempting to send more than balance
        val result = InputValidation.isValidAmount("150.0", 100.0)
        assertFalse(result.first)
        assertEquals("Insufficient balance", result.second)
    }
}
