package com.example.coworking.data.data_models.confirm_booking.request

import java.util.Date

data class ConfirmBookingRequestBody(
    val date: Date,
    val slotId: Int,
    val workspaceId: Int,
    val type: Int
)
