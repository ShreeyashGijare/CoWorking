package com.example.coworking.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coworking.R
import com.example.coworking.data.data_models.get_slots.Slot
import com.example.coworking.databinding.FragmentLoginBinding
import com.example.coworking.ui.viewmodel.LoginViewModel
import com.example.coworking.utils.UserManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLoginBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
        mBinding.etPassword.setTransFormation(PasswordTransformationMethod())

        setObserver()

        mBinding.tvLblCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }

        mBinding.btnLogIn.setOnClickListener {
            loginViewModel.loginUser(
                mBinding.etMobileNumber.getData(),
                mBinding.etPassword.getData()
            )
        }
    }

    private fun setObserver() {
        loginViewModel._responseLogin.observe(viewLifecycleOwner) {
            UserManager.setCurrentCustomerId(it.user_id, mBinding.etMobileNumber.getData())
            findNavController().navigate(R.id.nav_main_content)
        }

        loginViewModel.errorData.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
    }
}