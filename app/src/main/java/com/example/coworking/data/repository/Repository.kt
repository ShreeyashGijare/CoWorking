package com.example.coworking.data.repository

import com.example.coworking.data.data_models.confirm_booking.request.ConfirmBookingRequestBody
import com.example.coworking.data.data_models.confirm_booking.response.ConfirmBookingResponseBody
import com.example.coworking.data.data_models.create_account.request.CreateAccountRequestBody
import com.example.coworking.data.data_models.create_account.response.CreateAccountResponseBody
import com.example.coworking.data.data_models.get_slot_availability.GetSlotsAvailabilityResponse
import com.example.coworking.data.data_models.get_slots.GetSlotsResponse
import com.example.coworking.data.data_models.get_user_booking.GetUserBookingResponse
import com.example.coworking.data.data_models.login.request.LoginRequestBody
import com.example.coworking.data.data_models.login.response.LoginResponse
import java.util.Date

interface Repository {

    suspend fun userLogin(loginRequestBody: LoginRequestBody): LoginResponse

    suspend fun createUserAccount(createAccountRequestBody: CreateAccountRequestBody): CreateAccountResponseBody

    suspend fun getAvailableSlots(date: Date): GetSlotsResponse

    suspend fun getSlotAvailability(
        date: Date,
        slotId: Int,
        type: Int
    ): GetSlotsAvailabilityResponse

    suspend fun confirmSlotBooking(confirmBookingRequestBody: ConfirmBookingRequestBody): ConfirmBookingResponseBody

    suspend fun getUserBookings(userId: Int): GetUserBookingResponse

}