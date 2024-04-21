package com.example.coworking.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coworking.R
import com.example.coworking.data.data_models.get_slot_availability.Availability
import com.example.coworking.data.data_models.get_slots.Slot
import com.example.coworking.databinding.ItemDateAndSlotBinding
import com.example.coworking.ui.interfaces.SlotClickListener

class AvailableSlotsAdapter(
    private val context: Context,
    private val slotClickListener: SlotClickListener
) : RecyclerView.Adapter<AvailableSlotsAdapter.MainViewHolder>() {

    private var availableSlotsList: MutableList<Availability> = ArrayList()
    private var selectedSlot = -1

    class MainViewHolder(var binding: ItemDateAndSlotBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDateAndSlotBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (!availableSlotsList.isNullOrEmpty()) {
            return availableSlotsList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.tvTimeSlot.text = availableSlotsList[position].workspace_name

        if (selectedSlot == position) {
            holder.binding.clMain.setBackgroundResource(R.drawable.dark_blue_background)
            holder.binding.tvTimeSlot.setTextColor(context.getColor(R.color.white))
        } else {
            when (availableSlotsList[position].workspace_active) {
                true -> {
                    holder.binding.clMain.isClickable = true
                    holder.binding.clMain.isEnabled = true
                    holder.binding.tvTimeSlot.setTextColor(context.getColor(R.color.tertiary_text_color))
                    holder.binding.clMain.setBackgroundResource(R.drawable.light_blue_background_with_border)
                }

                false -> {
                    holder.binding.clMain.isClickable = false
                    holder.binding.clMain.isEnabled = false
                    holder.binding.tvTimeSlot.setTextColor(context.getColor(R.color.white))
                    holder.binding.clMain.setBackgroundResource(R.drawable.medium_gray_background)
                }
            }
        }
        holder.itemView.setOnClickListener {
            selectedSlot = position
            slotClickListener.availableSlotClickListener(availableSlotsList[position])
            notifyDataSetChanged()
        }
    }

    fun updateList(availableSlotsList: ArrayList<Availability>) {
        this.availableSlotsList = availableSlotsList
        notifyDataSetChanged()
    }
}