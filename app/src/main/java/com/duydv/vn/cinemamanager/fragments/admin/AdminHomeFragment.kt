package com.duydv.vn.cinemamanager.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment(), View.OnClickListener {

    private var mFragmentAdminHomeBinding: FragmentAdminHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentAdminHomeBinding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return mFragmentAdminHomeBinding!!.root
    }

    override fun onClick(v: View) {

    }
}