package com.example.snapbin.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.snapbin.data.rules.Validator

class LoginViewModel: ViewModel() {
    private val  TAG = LoginViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    fun onEvent(event:UIEvent){
        when(event){
            is UIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
                validateDataWithRules()
                printState()
            }

            is UIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
                validateDataWithRules()
                printState()
            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                validateDataWithRules()
                printState()
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                validateDataWithRules()
                printState()
            }
            is UIEvent.ConfirmPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    confirmPassword = event.confirmPasswordChanged
                )
                validateDataWithRules()
                printState()
            }

            is UIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }

    }
    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()

        validateDataWithRules()

    }

    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )

        val lNameResult = Validator.validateLastName(
            lName = registrationUIState.value.lastName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        val confirmPasswordResult = Validator.validateConfirmPassword(
            password = registrationUIState.value.password,
            confirmPassword = registrationUIState.value.confirmPassword
        )



        Log.d(TAG, "INsidevalidator")
        Log.d(TAG, "fNameResult = $fNameResult")
        Log.d(TAG, "lNameResult = $lNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")
        Log.d(TAG, "confirmPasswordResult = $confirmPasswordResult")

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            confirmPasswordChangedError = confirmPasswordResult.status
        )

        if (fNameResult.status && lNameResult.status && emailResult.status && passwordResult.status && confirmPasswordResult.status)
        {
            allValidationsPassed.value = true
        }
        else
        {
            allValidationsPassed.value = false
        }

    }


    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }


}