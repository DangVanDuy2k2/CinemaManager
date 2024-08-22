package com.duydv.vn.cinemamanager.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.databinding.FragmentAdminFoodBinding

class AdminFoodFragment : Fragment() {

    private var mFragmentAdminFoodBinding: FragmentAdminFoodBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentAdminFoodBinding = FragmentAdminFoodBinding.inflate(inflater, container, false)
        return mFragmentAdminFoodBinding!!.root
    }
}