package com.example.snapbin

import com.example.snapbin.data.rules.Validator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatorTest {

    // --- First Name ---

    @Test
    fun `validateFirstName passes for name with 3 or more characters`() {
        assertTrue(Validator.validateFirstName("John").status)
    }

    @Test
    fun `validateFirstName fails for name shorter than 3 characters`() {
        assertFalse(Validator.validateFirstName("Jo").status)
    }

    @Test
    fun `validateFirstName fails for empty string`() {
        assertFalse(Validator.validateFirstName("").status)
    }

    // --- Last Name ---

    @Test
    fun `validateLastName passes for name with 3 or more characters`() {
        assertTrue(Validator.validateLastName("Doe").status)
    }

    @Test
    fun `validateLastName fails for short name`() {
        assertFalse(Validator.validateLastName("D").status)
    }

    // --- Email ---

    @Test
    fun `validateEmail passes for valid email`() {
        assertTrue(Validator.validateEmail("user@example.com").status)
    }

    @Test
    fun `validateEmail fails for missing domain`() {
        assertFalse(Validator.validateEmail("user@").status)
    }

    @Test
    fun `validateEmail fails for missing at-sign`() {
        assertFalse(Validator.validateEmail("userexample.com").status)
    }

    @Test
    fun `validateEmail fails for empty string`() {
        assertFalse(Validator.validateEmail("").status)
    }

    // --- Password ---

    @Test
    fun `validatePassword passes for strong password`() {
        assertTrue(Validator.validatePassword("Secure1!").status)
    }

    @Test
    fun `validatePassword fails when no uppercase letter`() {
        assertFalse(Validator.validatePassword("secure1!").status)
    }

    @Test
    fun `validatePassword fails when no digit`() {
        assertFalse(Validator.validatePassword("Secure!!").status)
    }

    @Test
    fun `validatePassword fails when no special character`() {
        assertFalse(Validator.validatePassword("Secure11").status)
    }

    @Test
    fun `validatePassword fails when shorter than 6 characters`() {
        assertFalse(Validator.validatePassword("S1!").status)
    }

    @Test
    fun `validatePassword fails when contains whitespace`() {
        assertFalse(Validator.validatePassword("Secure 1!").status)
    }

    // --- Confirm Password ---

    @Test
    fun `validateConfirmPassword passes when passwords match and are strong`() {
        assertTrue(Validator.validateConfirmPassword("Secure1!", "Secure1!").status)
    }

    @Test
    fun `validateConfirmPassword fails when passwords do not match`() {
        assertFalse(Validator.validateConfirmPassword("Secure1!", "Other1!").status)
    }

    @Test
    fun `validateConfirmPassword fails when password is weak even if they match`() {
        assertFalse(Validator.validateConfirmPassword("weak", "weak").status)
    }

    // --- Privacy Policy ---

    @Test
    fun `validatePrivacyPolicyAcceptance passes when accepted`() {
        assertTrue(Validator.validatePrivacyPolicyAcceptance(true).status)
    }

    @Test
    fun `validatePrivacyPolicyAcceptance fails when not accepted`() {
        assertFalse(Validator.validatePrivacyPolicyAcceptance(false).status)
    }
}
