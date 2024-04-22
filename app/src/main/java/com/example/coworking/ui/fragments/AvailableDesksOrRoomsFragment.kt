package com.example.coworking.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coworking.R
import com.example.coworking.data.data_models.confirm_booking.request.ConfirmBookingRequestBody
import com.example.coworking.data.data_models.get_slot_availability.Availability
import com.example.coworking.data.data_models.get_slots.Slot
import com.example.coworking.databinding.FragmentAvailableDesksOrRoomsBinding
import com.example.coworking.databinding.GlobalPopupDialogBinding
import com.example.coworking.ui.adapter.AvailableSlotsAdapter
import com.example.coworking.ui.interfaces.SlotClickListener
import com.example.coworking.ui.viewmodel.SlotsViewModel
import com.example.coworking.utils.Constants.convertStringToDate
import com.example.coworking.utils.GlobalSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class AvailableDesksOrRoomsFragment : Fragment(), SlotClickListener {

    private lateinit var mBinding: FragmentAvailableDesksOrRoomsBinding
    private lateinit var alertDialogBinding: GlobalPopupDialogBinding
    private lateinit var confirmBookingDialog: AlertDialog
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
        slotsViewModel.getSlotAvailability(
            convertStringToDate(args.date),
            args.Slot.slot_id,
            args.TypeId
        )

        mBinding.btnBookDesk.setOnClickListener {
            if (selectedRoomOrDesk == null) {
                val errorMessage =
                    if (args.TypeId == 1) getString(R.string.error_select_a_desk) else getString(R.string.error_select_a_room)
                GlobalSnackBar.showSnackBar(
                    mBinding.root,
                    errorMessage,
                    false
                )
            } else {
                confirmAlertDialog()
            }
        }
    }

    private fun setObserver() {
        slotsViewModel._availableSlots.observe(viewLifecycleOwner) {
            availableSlotsAdapter.updateList(it.availability as ArrayList<Availability>)
        }

        slotsViewModel._confirmBookingResponse.observe(viewLifecycleOwner) {
            confirmBookingDialog.dismiss()
            GlobalSnackBar.showSnackBar(mBinding.root, it.message, true)
            findNavController().navigate(R.id.action_availableDesksOrRoomsFragment_to_homeFragment)
        }

        slotsViewModel.errorData.observe(viewLifecycleOwner) {
            GlobalSnackBar.showSnackBar(
                mBinding.root,
                it,
                false
            )
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

    private fun confirmAlertDialog() {
        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(requireActivity(), R.style.PopupTheme)
        alertDialogBinding = GlobalPopupDialogBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(alertDialogBinding.root)

        confirmBookingDialog = alertDialogBuilder.create()

        alertDialogBinding.tvDeskId.text =
            requireContext().getString(R.string.popup_desk_id, args.Slot.slot_id.toString())
        alertDialogBinding.tvDeskNumber.text =
            requireContext().getString(
                R.string.popup_desk_number,
                selectedRoomOrDesk!!.workspace_id.toString()
            )
        alertDialogBinding.tvSlotDetails.text =
            requireContext().getString(
                R.string.popup_slot_details,
                args.date,
                args.Slot.slot_name
            )

        alertDialogBinding.btnConfirm.setOnClickListener {
            val slotToBeBooked = ConfirmBookingRequestBody(
                date = Date(2024, 5, 21),
                slotId = args.Slot.slot_id,
                workspaceId = selectedRoomOrDesk!!.workspace_id,
                type = args.TypeId
            )
            slotsViewModel.confirmBooking(slotToBeBooked)
        }

        alertDialogBinding.ivCancel.setOnClickListener {
            confirmBookingDialog.dismiss()
        }
        confirmBookingDialog.show()
    }


}