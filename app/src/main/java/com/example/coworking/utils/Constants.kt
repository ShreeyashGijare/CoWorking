package com.example.coworking.utils

import java.text.SimpleDateFormat
import java.util.Date

object Constants {

    const val BASE_URL: String = "http://demo0413095.mockable.io/digitalflake/api/"


    fun convertDateToString(selectedDate: Date): String {
        val outputFormat = SimpleDateFormat("YYYY MM dd")
        return outputFormat.format(selectedDate)
    }

    fun convertStringToDate(selectedDateString: String): Date {
        val outputFormat = SimpleDateFormat("YYYY MM dd")
        return outputFormat.parse(selectedDateString)
    }

}