package com.duydv.vn.cinemamanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.databinding.FragmentBookingBinding

class BookingFragment : Fragment() {
    private var mFragmentBookingBinding: FragmentBookingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentBookingBinding = FragmentBookingBinding.inflate(inflater, container, false)
        return mFragmentBookingBinding!!.root
    }
}