package com.duydv.vn.cinemamanager.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.activitys.ChangePasswordActivity
import com.duydv.vn.cinemamanager.activitys.SignInActivity
import com.duydv.vn.cinemamanager.constant.GlobalFunction.startActivity
import com.duydv.vn.cinemamanager.databinding.FragmentAdminManageBinding
import com.duydv.vn.cinemamanager.prefs.DataStoreManager
import com.google.firebase.auth.FirebaseAuth

class AdminManageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentAdminManageBinding =
            FragmentAdminManageBinding.inflate(inflater, container, false)
        fragmentAdminManageBinding.tvEmail.text = DataStoreManager.getUser()?.email
        fragmentAdminManageBinding.layoutSignOut.setOnClickListener { onClickSignOut() }
        fragmentAdminManageBinding.layoutChangePassword.setOnClickListener { onClickChangePassword() }
        return fragmentAdminManageBinding.root
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