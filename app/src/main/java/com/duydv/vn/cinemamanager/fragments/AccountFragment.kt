package com.duydv.vn.cinemamanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.activitys.ChangePasswordActivity
import com.duydv.vn.cinemamanager.activitys.SignInActivity
import com.duydv.vn.cinemamanager.constant.GlobalFunction.startActivity
import com.duydv.vn.cinemamanager.databinding.FragmentAccountBinding
import com.duydv.vn.cinemamanager.prefs.DataStoreManager
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        fragmentAccountBinding.tvEmail.text = DataStoreManager.getUser()?.email
        fragmentAccountBinding.layoutSignOut.setOnClickListener { onClickSignOut() }
        fragmentAccountBinding.layoutChangePassword.setOnClickListener { onClickChangePassword() }
        return fragmentAccountBinding.root
    }

    private fun onClickChangePassword() {
        startActivity(activity, ChangePasswordActivity::class.java)
    }

    private fun onClickSignOut() {
        if (activity == null) {
            return
        }
        FirebaseAuth.getInstance().signOut()
        DataStoreManager.setUser(null)
        startActivity(activity, SignInActivity::class.java)
        requireActivity().finishAffinity()
    }
}