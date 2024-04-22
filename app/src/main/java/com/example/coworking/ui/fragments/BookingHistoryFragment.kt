package com.example.coworking.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coworking.data.data_models.get_user_booking.Booking
import com.example.coworking.databinding.FragmentBookingHistoryBinding
import com.example.coworking.ui.adapter.UserBookingHistoryAdapter
import com.example.coworking.ui.viewmodel.BookingViewModel
import com.example.coworking.utils.GlobalSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingHistoryFragment : Fragment() {

    private lateinit var mBinding: FragmentBookingHistoryBinding
    private val bookingViewModel by viewModels<BookingViewModel>()

    private lateinit var userBookingHistoryAdapter: UserBookingHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBookingHistoryBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setObserver()
        bookingViewModel.getUserBookings()
        mBinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpAdapter() {
        userBookingHistoryAdapter = UserBookingHistoryAdapter(requireActivity())
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mBinding.rvUserBooking.layoutManager = linearLayoutManager
        mBinding.rvUserBooking.adapter = userBookingHistoryAdapter
    }

    private fun setObserver() {
        bookingViewModel._userBookingData.observe(viewLifecycleOwner) {
            userBookingHistoryAdapter.updateList(it.bookings as ArrayList<Booking>)
        }

        bookingViewModel.errorData.observe(viewLifecycleOwner) {
            GlobalSnackBar.showSnackBar(
                mBinding.root,
                it,
                false
            )
        }
    }
}