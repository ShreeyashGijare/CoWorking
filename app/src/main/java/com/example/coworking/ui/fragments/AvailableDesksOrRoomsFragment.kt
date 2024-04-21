package com.example.coworking.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.coworking.R
import com.example.coworking.databinding.FragmentAvailableDesksOrRoomsBinding

class AvailableDesksOrRoomsFragment : Fragment() {

    private lateinit var mBinding: FragmentAvailableDesksOrRoomsBinding
    private val args: AvailableDesksOrRoomsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAvailableDesksOrRoomsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(
            requireActivity(),
            "${args.Slot.slot_name} + ${args.TypeId}",
            Toast.LENGTH_SHORT
        ).show()
    }

}