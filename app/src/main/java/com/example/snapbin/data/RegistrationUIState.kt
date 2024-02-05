package com.example.snapbin.data

data class RegistrationUIState(
    var firstName :String = "",
    var lastName  :String = "",
    var email  :String = "",
    var password  :String = "",
    var confirmPassword: String = "",

    var firstNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var confirmPasswordChangedError : Boolean = false
)
