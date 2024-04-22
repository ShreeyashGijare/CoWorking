package com.example.coworking.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coworking.R
import com.example.coworking.data.data_models.get_slot_availability.Availability
import com.example.coworking.data.data_models.get_slots.Slot
import com.example.coworking.databinding.FragmentSelectDateAndSlotBinding
import com.example.coworking.ui.adapter.DateAndSlotAdapter
import com.example.coworking.ui.interfaces.SlotClickListener
import com.example.coworking.ui.viewmodel.SlotsViewModel
import com.example.coworking.utils.GlobalSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class SelectDateAndSlotFragment : Fragment(), SlotClickListener {

    private lateinit var mBinding: FragmentSelectDateAndSlotBinding
    val args: SelectDateAndSlotFragmentArgs by navArgs()
    private val slotsViewModel by viewModels<SlotsViewModel>()
    private lateinit var dateAndSlotAdapter: DateAndSlotAdapter

    private var selectedType: Int = 0
    private var selectedSlot: Slot? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSelectDateAndSlotBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedType = args.TypeId
        setObserver()
        setUpAdapter()
        slotsViewModel.getAvailableSlotsForTheDate(Date(2024, 5, 21))

        mBinding.btnNext.setOnClickListener {
            if (selectedSlot == null) {
                GlobalSnackBar.showSnackBar(
                    mBinding.root,
                    requireActivity().getString(R.string.error_select_a_slot),
                    false
                )
            } else {
                val action =
                    SelectDateAndSlotFragmentDirections.actionSelectDateAndSlotFragmentToAvailableDesksOrRoomsFragment(
                        selectedSlot!!,
                        selectedType,
                        ""
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun setObserver() {
        slotsViewModel._availableSlotsForDate.observe(viewLifecycleOwner) {
            dateAndSlotAdapter.updateList(it.slots as ArrayList<Slot>)
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
        dateAndSlotAdapter = DateAndSlotAdapter(requireActivity(), this)
        val gridLayoutManager =
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        mBinding.rvAvailableSlots.layoutManager = gridLayoutManager
        mBinding.rvAvailableSlots.adapter = dateAndSlotAdapter
    }

    override fun dateAndSlotClickListener(slot: Slot) {
        selectedSlot = slot
    }

    override fun availableSlotClickListener(availability: Availability) {
        Log.i("TAG", ": Not Required for now")
    }
}