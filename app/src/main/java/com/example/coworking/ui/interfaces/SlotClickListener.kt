package com.example.coworking.ui.interfaces

import com.example.coworking.data.data_models.get_slot_availability.Availability
import com.example.coworking.data.data_models.get_slots.Slot

interface SlotClickListener  {
    fun dateAndSlotClickListener(slot: Slot)
    fun availableSlotClickListener(availability: Availability)
}