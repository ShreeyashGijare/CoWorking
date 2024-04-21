package com.example.coworking.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworking.data.data_models.get_user_booking.GetUserBookingResponse
import com.example.coworking.data.repository.Repository
import com.example.coworking.utils.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val userBookingJob: Job? = null
    private var userBookingData: MutableLiveData<GetUserBookingResponse> = MutableLiveData()
    val _userBookingData: LiveData<GetUserBookingResponse> = userBookingData


    fun getUserBookings() {
        userBookingJob?.cancel()
        viewModelScope.async(Dispatchers.IO) {
            val userBooking = repository.getUserBookings(UserManager.currentUser.userId!!)
            withContext(Dispatchers.Main) {
                userBookingData.value = userBooking
            }
        }
    }
}