package com.example.coworking.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworking.data.data_models.create_account.request.CreateAccountRequestBody
import com.example.coworking.data.data_models.create_account.response.CreateAccountResponseBody
import com.example.coworking.data.data_models.login.request.LoginRequestBody
import com.example.coworking.data.data_models.login.response.LoginResponse
import com.example.coworking.data.repository.Repository
import com.example.coworking.utils.ValidationResult
import com.example.coworking.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private var createAccountJob: Job? = null
    private var responseCreateAccount: MutableLiveData<CreateAccountResponseBody> =
        MutableLiveData()
    val _responseCreateAccount: LiveData<CreateAccountResponseBody> = responseCreateAccount

    private var loginJob: Job? = null
    private var responseLogin: MutableLiveData<LoginResponse> = MutableLiveData()
    val _responseLogin: LiveData<LoginResponse> = responseLogin


    var errorData: MutableLiveData<String> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        errorData.value = exception.localizedMessage
    }

    //Create User
    fun validateUserAndCreateAccount(fullName: String, mobileNumber: String, email: String) {
        val validateFullName = validateName(fullName)
        val validateNumber = validateNumber(mobileNumber)
        val validateEmail = validateEmail(email)

        if (validateFullName.status && validateNumber.status && validateEmail.status) {
            createAccount(fullName, mobileNumber, email)
        } else {
            if (!validateFullName.status) {
                errorData.value = validateFullName.errorMessage
            } else if (!validateNumber.status) {
                errorData.value = validateNumber.errorMessage
            } else {
                errorData.value = validateEmail.errorMessage
            }
        }
    }

    private fun createAccount(fullName: String, mobileNumber: String, email: String) {
        createAccountJob?.cancel()
        createAccountJob = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val createAccountDeferred = viewModelScope.async {
                val createAccountRequestBody = CreateAccountRequestBody(
                    email = email,
                    name = fullName
                )
                repository.createUserAccount(createAccountRequestBody)
            }
            withContext(Dispatchers.Main) {
                try {
                    responseCreateAccount.value = createAccountDeferred.await()
                } catch (e: Exception) {
                    errorData.value = e.message
                }
            }
        }
    }

    private fun validateName(fullName: String): ValidationResult {
        return Validator.userNameValidation(fullName)
    }

    private fun validateNumber(phoneNumber: String): ValidationResult {
        return Validator.numberValidation(number = phoneNumber)
    }

    private fun validateEmail(email: String): ValidationResult {
        return Validator.emailValidation(email = email)
    }


    //Login
    fun loginUser(emailOrPhoneNumber: String, password: String) {
        if (emailOrPhoneNumber.isNotEmpty() && password.isNotEmpty()) {
            loginJob?.cancel()
            loginJob = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                val loginDeferred = viewModelScope.async {
                    val loginRequestBody = LoginRequestBody(
                        email = emailOrPhoneNumber,
                        password = password
                    )
                    repository.userLogin(loginRequestBody)
                }
                withContext(Dispatchers.Main) {
                    try {
                        responseLogin.value = loginDeferred.await()
                    } catch (e: Exception) {
                        errorData.value = e.message
                    }
                }
            }
        }
    }
}