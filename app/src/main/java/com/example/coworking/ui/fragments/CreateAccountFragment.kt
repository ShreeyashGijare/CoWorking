package com.example.coworking.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coworking.R
import com.example.coworking.databinding.FragmentCreateAccountBinding
import com.example.coworking.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private lateinit var mBinding: FragmentCreateAccountBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCreateAccountBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.etMobileNumber.setInputType(InputType.TYPE_CLASS_NUMBER)

        setObserver()

        mBinding.tvLblExistingUser.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
        }
        mBinding.btnLogIn.setOnClickListener {
            loginViewModel.validateUserAndCreateAccount(
                mBinding.etFullName.getData(),
                mBinding.etMobileNumber.getData(),
                mBinding.etEmailId.getData()
            )
            Log.i("TAG", "onViewCreated: ${mBinding.etFullName.getData()} ")
            Log.i("TAG", "onViewCreated: ${mBinding.etMobileNumber.getData()} ")
            Log.i("TAG", "onViewCreated: ${mBinding.etEmailId.getData()} ")
        }
    }

    private fun setObserver() {
        loginViewModel.errorData.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        loginViewModel._responseCreateAccount.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }
}