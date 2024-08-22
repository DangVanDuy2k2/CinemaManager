package com.duydv.vn.cinemamanager.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.databinding.FragmentAdminBookingBinding

class AdminBookingFragment : Fragment() {

    private var mFragmentAdminBookingBinding: FragmentAdminBookingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentAdminBookingBinding =
            FragmentAdminBookingBinding.inflate(inflater, container, false)

        return mFragmentAdminBookingBinding!!.root
    }
}