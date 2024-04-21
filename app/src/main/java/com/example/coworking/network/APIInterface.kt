package com.example.coworking.network

import com.example.coworking.data.data_models.confirm_booking.request.ConfirmBookingRequestBody
import com.example.coworking.data.data_models.confirm_booking.response.ConfirmBookingResponseBody
import com.example.coworking.data.data_models.create_account.request.CreateAccountRequestBody
import com.example.coworking.data.data_models.create_account.response.CreateAccountResponseBody
import com.example.coworking.data.data_models.get_slot_availability.GetSlotsAvailabilityResponse
import com.example.coworking.data.data_models.get_slots.GetSlotsResponse
import com.example.coworking.data.data_models.get_user_booking.GetUserBookingResponse
import com.example.coworking.data.data_models.login.request.LoginRequestBody
import com.example.coworking.data.data_models.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date

interface APIInterface {

    @POST("login")
    suspend fun userLogin(@Body loginRequestBody: LoginRequestBody): LoginResponse

    @POST("create_account")
    suspend fun createUserAccount(@Body createAccountRequestBody: CreateAccountRequestBody) : CreateAccountResponseBody

    @GET("get_slots")
    suspend fun getAvailableSlots(
        @Query("date") date: Date
    ): GetSlotsResponse

    @GET("get_availability")
    suspend fun getSlotAvailability(
        @Query("date") date: Date,
        @Query("slot_id") slotId: Int,
        @Query("type") type: Int
    ): GetSlotsAvailabilityResponse

    @POST("confirm_booking")
    suspend fun confirmSlotBooking(@Body confirmBookingRequestBody: ConfirmBookingRequestBody): ConfirmBookingResponseBody


    @GET("get_bookings")
    suspend fun getUserBookings(
        @Query("user_id") userId: Int
    ): GetUserBookingResponse
}