package com.example.snapbin.data.rules

import java.util.regex.Pattern

object Validator {

    fun validateFirstName(fName: String): ValidationResult{
        return ValidationResult(
            (!fName.isNullOrEmpty() && fName.length>=3)
        )

    }
    fun validateLastName(lName: String): ValidationResult{
        return ValidationResult(
            (!lName.isNullOrEmpty() && lName.length>=3)
        )

    }
    fun validateEmail(email: String): ValidationResult {
        val emailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        )

        return ValidationResult(
            !email.isNullOrEmpty() && emailPattern.matcher(email).matches()
        )
    }

    fun validatePassword(password: String): ValidationResult {
        val regex = Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=\\S+$).{6,}$")
        return ValidationResult(
            password.matches(regex)
        )
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        val regex = Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=\\S+$).{6,}$")
        return ValidationResult(
            password.matches(regex) && password == confirmPassword
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue:Boolean):ValidationResult{
        return ValidationResult(
            statusValue
        )
    }
}

data class ValidationResult(
    val status: Boolean = false
)