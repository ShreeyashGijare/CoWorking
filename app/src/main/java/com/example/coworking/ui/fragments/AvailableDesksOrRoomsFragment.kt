package com.example.coworking.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coworking.R
import com.example.coworking.data.data_models.confirm_booking.request.ConfirmBookingRequestBody
import com.example.coworking.data.data_models.get_slot_availability.Availability
import com.example.coworking.data.data_models.get_slots.Slot
import com.example.coworking.databinding.FragmentAvailableDesksOrRoomsBinding
import com.example.coworking.ui.adapter.AvailableSlotsAdapter
import com.example.coworking.ui.interfaces.SlotClickListener
import com.example.coworking.ui.viewmodel.SlotsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class AvailableDesksOrRoomsFragment : Fragment(), SlotClickListener {

    private lateinit var mBinding: FragmentAvailableDesksOrRoomsBinding
    private val args: AvailableDesksOrRoomsFragmentArgs by navArgs()
    private val slotsViewModel by viewModels<SlotsViewModel>()

    private lateinit var availableSlotsAdapter: AvailableSlotsAdapter

    private var selectedRoomOrDesk: Availability? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAvailableDesksOrRoomsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (args.TypeId) {
            1 -> mBinding.tvAvailableOptions.text =
                requireActivity().getString(R.string.available_desks)

            2 -> mBinding.tvAvailableOptions.text =
                requireActivity().getString(R.string.available_rooms)
        }

        setUpAdapter()
        setObserver()
        slotsViewModel.getSlotAvailability(Date(2024, 5, 21), args.Slot.slot_id, args.TypeId)

        mBinding.btnBookDesk.setOnClickListener {
            if (selectedRoomOrDesk == null) {
                val toastMessage =
                    if (args.TypeId == 1) "Please select a desk" else "Please select a room"
                Toast.makeText(requireActivity(), toastMessage, Toast.LENGTH_SHORT).show()
            } else {
                val slotToBeBooked = ConfirmBookingRequestBody(
                    date = Date(2024, 5, 21),
                    slotId = args.Slot.slot_id,
                    workspaceId = selectedRoomOrDesk!!.workspace_id,
                    type = args.TypeId
                )
                slotsViewModel.confirmBooking(slotToBeBooked)
            }
        }
    }

    private fun setObserver() {
        slotsViewModel._availableSlots.observe(viewLifecycleOwner) {
            availableSlotsAdapter.updateList(it.availability as ArrayList<Availability>)
        }

        slotsViewModel._confirmBookingResponse.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpAdapter() {
        availableSlotsAdapter = AvailableSlotsAdapter(requireActivity(), this)
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 6, GridLayoutManager.VERTICAL, false)
        mBinding.rvSlotAvailable.layoutManager = gridLayoutManager
        mBinding.rvSlotAvailable.adapter = availableSlotsAdapter
    }

    override fun dateAndSlotClickListener(slot: Slot) {
        Log.i("TAG", ": not required for now")
    }

    override fun availableSlotClickListener(availability: Availability) {
        selectedRoomOrDesk = availability
    }
}