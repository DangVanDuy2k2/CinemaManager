package com.duydv.vn.cinemamanager.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duydv.vn.cinemamanager.databinding.FragmentAdminCategoryBinding

class AdminCategoryFragment : Fragment() {

    private var mFragmentAdminCategoryBinding: FragmentAdminCategoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentAdminCategoryBinding =
            FragmentAdminCategoryBinding.inflate(inflater, container, false)
        return mFragmentAdminCategoryBinding!!.root
    }
}