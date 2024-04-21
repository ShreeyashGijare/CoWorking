package com.example.coworking.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coworking.R
import com.example.coworking.data.data_models.get_user_booking.Booking
import com.example.coworking.databinding.ItemUserBookingHistoryBinding

class UserBookingHistoryAdapter(
    private val context: Context
) : RecyclerView.Adapter<UserBookingHistoryAdapter.MainViewHolder>() {
    class MainViewHolder(var binding: ItemUserBookingHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var userBookingList: MutableList<Booking> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBookingHistoryBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (!userBookingList.isNullOrEmpty()) {
            return userBookingList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.binding.tvDeskId.text = context.getString(
            R.string.set_strings_starting_with_colon,
            userBookingList[position].workspace_id.toString()
        )

        holder.binding.tvBookingDate.text = context.getString(
            R.string.set_strings_starting_with_colon,
            userBookingList[position].booking_date
        )

        holder.binding.tvName.text = context.getString(
            R.string.set_strings_starting_with_colon,
            userBookingList[position].workspace_name
        )
    }

    fun updateList(userBookingList: ArrayList<Booking>) {
        this.userBookingList = userBookingList
        notifyDataSetChanged()
    }


}