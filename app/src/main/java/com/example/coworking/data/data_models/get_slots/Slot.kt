package com.example.coworking.data.data_models.get_slots

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Slot(
    val slot_active: Boolean,
    val slot_id: Int,
    val slot_name: String
) : Parcelable