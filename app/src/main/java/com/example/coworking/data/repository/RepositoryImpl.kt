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
import com.example.coworking.network.APIInterface
import java.util.Date

class RepositoryImpl(
    private val apiInterface: APIInterface
) : Repository {
    override suspend fun userLogin(loginRequestBody: LoginRequestBody): LoginResponse {
        return apiInterface.userLogin(loginRequestBody)
    }

    override suspend fun createUserAccount(createAccountRequestBody: CreateAccountRequestBody): CreateAccountResponseBody {
        return apiInterface.createUserAccount(createAccountRequestBody)
    }

    override suspend fun getAvailableSlots(date: Date): GetSlotsResponse {
        return apiInterface.getAvailableSlots(date)
    }

    override suspend fun getSlotAvailability(
        date: Date,
        slotId: Int,
        type: Int
    ): GetSlotsAvailabilityResponse {
        return getSlotAvailability(date, slotId, type)
    }

    override suspend fun confirmSlotBooking(confirmBookingRequestBody: ConfirmBookingRequestBody): ConfirmBookingResponseBody {
        return apiInterface.confirmSlotBooking(confirmBookingRequestBody)
    }

    override suspend fun getUserBookings(userId: Int): GetUserBookingResponse {
        return apiInterface.getUserBookings(userId)
    }


}