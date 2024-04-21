package com.example.coworking.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private var availableSlots: MutableLiveData<GetSlotsResponse> = MutableLiveData()
    val _availableSlots: LiveData<GetSlotsResponse> = availableSlots

    fun getAvailableSlotsForTheData(date: Date) {
        getSlotsJob?.cancel()
        getSlotsJob = viewModelScope.async(Dispatchers.IO) {
            val getSlots = repository.getAvailableSlots(date)
            withContext(Dispatchers.Main) {
                availableSlots.value = getSlots
            }
        }
    }
}