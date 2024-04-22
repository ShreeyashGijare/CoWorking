package com.example.coworking.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coworking.data.data_models.confirm_booking.request.ConfirmBookingRequestBody
import com.example.coworking.data.data_models.confirm_booking.response.ConfirmBookingResponseBody
import com.example.coworking.data.data_models.get_slot_availability.GetSlotsAvailabilityResponse
import com.example.coworking.data.data_models.get_slots.GetSlotsResponse
import com.example.coworking.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SlotsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private var getSlotsJob: Job? = null
    private var availableSlotsForDate: MutableLiveData<GetSlotsResponse> = MutableLiveData()
    val _availableSlotsForDate: LiveData<GetSlotsResponse> = availableSlotsForDate

    private var getAvailableSlots: Job? = null
    private var availableSlots: MutableLiveData<GetSlotsAvailabilityResponse> = MutableLiveData()
    val _availableSlots: LiveData<GetSlotsAvailabilityResponse> = availableSlots

    private var confirmBookingJob: Job? = null
    private var confirmBookingResponse: MutableLiveData<ConfirmBookingResponseBody> =
        MutableLiveData()
    val _confirmBookingResponse: LiveData<ConfirmBookingResponseBody> = confirmBookingResponse

    var errorData: MutableLiveData<String> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        errorData.value = exception.localizedMessage
    }


    fun getAvailableSlotsForTheDate(date: Date) {
        getSlotsJob?.cancel()
        getSlotsJob = viewModelScope.launch {
            val getSlotsDeferred = viewModelScope.async {
                repository.getAvailableSlots(date)
            }
            withContext(Dispatchers.Main) {
                try {
                    availableSlotsForDate.value = getSlotsDeferred.await()
                } catch (e: Exception) {
                    errorData.value = e.message
                }
            }
        }
    }

    fun getSlotAvailability(date: Date, slotId: Int, type: Int) {
        getAvailableSlots?.cancel()
        getAvailableSlots = viewModelScope.launch {
            val availableSlotDeferred = viewModelScope.async {
                repository.getSlotAvailability(date, slotId, type)
            }
            withContext(Dispatchers.Main) {
                try {
                    availableSlots.value = availableSlotDeferred.await()
                } catch (e: Exception) {
                    errorData.value = e.message
                }
            }
        }
    }

    fun confirmBooking(confirmBookingRequestBody: ConfirmBookingRequestBody) {
        confirmBookingJob?.cancel()
        confirmBookingJob = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val confirmBookingDeferred = viewModelScope.async {
                repository.confirmSlotBooking(confirmBookingRequestBody)
            }
            withContext(Dispatchers.Main) {
                try {
                    confirmBookingResponse.value = confirmBookingDeferred.await()
                } catch (e: Exception) {
                    errorData.value = e.message
                }
            }
        }
    }
}