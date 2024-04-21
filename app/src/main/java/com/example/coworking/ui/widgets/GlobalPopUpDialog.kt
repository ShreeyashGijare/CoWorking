package com.example.coworking.ui.widgets

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.coworking.R
import com.example.coworking.databinding.GlobalPopupDialogBinding

object GlobalPopUpDialog : DialogFragment() {


    private lateinit var globalAlertDialog: AlertDialog
    private lateinit var globalAlertDialogBuilder: AlertDialog.Builder
    private lateinit var mBinding: GlobalPopupDialogBinding


    private val DESK_ID: String = "DESK_ID"
    private val DESK_NUMBER: String = "DESK_NUMBER"
    private val SLOT_DETAILS: String = "SLOT_DETAILS"
    private var FRAGMENT_TAG: String = "FRAGMENT"

    private var deskId: Int = 0
    private var deskNumber: Int = 0
    private lateinit var slotDetails: String

    private lateinit var confirmClickListener: View.OnClickListener

    private fun setConfirmClickListener(saveClickListener: View.OnClickListener) {
        this.confirmClickListener = saveClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        globalAlertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.PopupTheme)
        val layoutInflater = requireActivity().layoutInflater
        mBinding = GlobalPopupDialogBinding.inflate(layoutInflater)
        globalAlertDialogBuilder.setView(mBinding.root)
        globalAlertDialog = globalAlertDialogBuilder.create()


        mBinding.tvDeskId.text =
            requireContext().getString(R.string.popup_desk_id, deskId.toString())
        mBinding.tvDeskNumber.text =
            requireContext().getString(R.string.popup_desk_number, deskNumber.toString())
        mBinding.tvSlotDetails.text =
            requireContext().getString(R.string.popup_slot_details, slotDetails, "")


        globalAlertDialog.show()
        return globalAlertDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            if (requireArguments().containsKey(DESK_ID)) {
                deskId = requireArguments().getInt(DESK_ID)!!
            }
            if (requireArguments().containsKey(DESK_NUMBER)) {
                deskNumber = requireArguments().getInt(DESK_NUMBER)!!
            }
            if (requireArguments().containsKey(SLOT_DETAILS)) {
                slotDetails = requireArguments().getString(SLOT_DETAILS)!!
            }
        }
    }


    fun showConfirmBookingDialog(
        fragmentManager: FragmentManager,
        deskId: Int,
        deskNumber: Int,
        slotDetails: String,
        confirmClickListener: View.OnClickListener
    ) {
        if (fragmentManager != null) {
            dismiss()
        }
        val globalPopUpDialog = GlobalPopUpDialog
        val bundle = Bundle()
        bundle.putInt(DESK_ID, deskId)
        bundle.putInt(DESK_NUMBER, deskNumber)
        bundle.putString(SLOT_DETAILS, slotDetails)
        globalPopUpDialog.setConfirmClickListener(confirmClickListener)
        if (fragmentManager != null) {
            globalPopUpDialog.show(fragmentManager, FRAGMENT_TAG)
        }
    }
}