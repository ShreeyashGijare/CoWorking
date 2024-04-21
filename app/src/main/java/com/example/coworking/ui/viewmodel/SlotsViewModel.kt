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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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


    fun getAvailableSlotsForTheDate(date: Date) {
        getSlotsJob?.cancel()
        getSlotsJob = viewModelScope.async(Dispatchers.IO) {
            val getSlots = repository.getAvailableSlots(date)
            withContext(Dispatchers.Main) {
                availableSlotsForDate.value = getSlots
            }
        }
    }

    fun getSlotAvailability(date: Date, slotId: Int, type: Int) {
        getAvailableSlots?.cancel()
        getAvailableSlots = viewModelScope.async {
            val availableSlot = repository.getSlotAvailability(date, slotId, type)
            withContext(Dispatchers.Main) {
                availableSlots.value = availableSlot
            }
        }
    }


    fun confirmBooking(confirmBookingRequestBody: ConfirmBookingRequestBody) {
        confirmBookingJob?.cancel()
        confirmBookingJob = viewModelScope.async(Dispatchers.IO) {
            val confirmBooking = repository.confirmSlotBooking(confirmBookingRequestBody)
            withContext(Dispatchers.Main) {
                confirmBookingResponse.value = confirmBooking
            }
        }
    }
}