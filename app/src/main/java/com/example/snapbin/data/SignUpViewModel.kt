package com.example.snapbin.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
import com.example.snapbin.data.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignUpViewModel: ViewModel() {
    private val  TAG = SignUpViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    fun onEvent(event:SignUpUIEvent){
        when(event){
            is SignUpUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
                validateDataWithRules()
                printState()
            }

            is SignUpUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
                validateDataWithRules()
                printState()
            }

            is SignUpUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                validateDataWithRules()
                printState()
            }

            is SignUpUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                validateDataWithRules()
                printState()
            }
            is SignUpUIEvent.ConfirmPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    confirmPassword = event.confirmPasswordChanged
                )
                validateDataWithRules()
                printState()
            }

            is SignUpUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicy = event.status
                )
                validateDataWithRules()
                printState()
            }

            is SignUpUIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }

    }
    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()
        createUserInFirebase(
            email = registrationUIState.value.email,
            password =registrationUIState.value.password
        )

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

        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicy
        )



        Log.d(TAG, "INsidevalidator")
        Log.d(TAG, "fNameResult = $fNameResult")
        Log.d(TAG, "lNameResult = $lNameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")
        Log.d(TAG, "confirmPasswordResult = $confirmPasswordResult")
        Log.d(TAG, "PrivacyPolicyAccepted = $privacyPolicyResult")

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            confirmPasswordChangedError = confirmPasswordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )

        allValidationsPassed.value = (fNameResult.status && lNameResult.status
                && emailResult.status && passwordResult.status
                && confirmPasswordResult.status && privacyPolicyResult.status)

    }


    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun createUserInFirebase(email:String,password:String){
        signUpInProgress.value=true
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, "isSuucessfull = ${it.isSuccessful}")

                signUpInProgress.value= false
                if(it.isSuccessful){
                    SnapBinAppRoute.navigateTo(Screen.HomeScreen)
                }
            }


            .addOnFailureListener{
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")

            }
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if(it.currentUser == null){
                Log.d(TAG, "Inside sign outsuccess")
                SnapBinAppRoute.navigateTo(Screen.LoginScreen)
            }
            else
            {
                Log.d(TAG, "Inside sign out is not completed")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }


}