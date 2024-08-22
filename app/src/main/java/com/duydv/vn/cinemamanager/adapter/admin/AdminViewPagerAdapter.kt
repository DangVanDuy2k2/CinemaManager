package com.duydv.vn.cinemamanager.adapter.admin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.duydv.vn.cinemamanager.fragments.admin.AdminBookingFragment
import com.duydv.vn.cinemamanager.fragments.admin.AdminCategoryFragment
import com.duydv.vn.cinemamanager.fragments.admin.AdminFoodFragment
import com.duydv.vn.cinemamanager.fragments.admin.AdminHomeFragment
import com.duydv.vn.cinemamanager.fragments.admin.AdminManageFragment

class AdminViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> AdminFoodFragment()
            2 -> AdminHomeFragment()
            3 -> AdminBookingFragment()
            4 -> AdminManageFragment()
            else -> AdminCategoryFragment()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}