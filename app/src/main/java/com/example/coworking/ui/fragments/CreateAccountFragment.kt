package com.example.coworking.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coworking.R
import com.example.coworking.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment() {

    private lateinit var mBinding: FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCreateAccountBinding.inflate(layoutInflater)
        return mBinding.root
    }


}