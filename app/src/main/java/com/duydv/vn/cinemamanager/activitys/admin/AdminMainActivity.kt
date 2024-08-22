package com.duydv.vn.cinemamanager.activitys.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.duydv.vn.cinemamanager.R
import com.duydv.vn.cinemamanager.activitys.BaseActivity
import com.duydv.vn.cinemamanager.adapter.admin.AdminViewPagerAdapter
import com.duydv.vn.cinemamanager.databinding.ActivityAdminMainBinding
import org.greenrobot.eventbus.EventBus

@SuppressLint("NonConstantResourceId")
class AdminMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityAdminMainBinding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(activityAdminMainBinding.root)
        val adminViewPagerAdapter = AdminViewPagerAdapter(this)
        activityAdminMainBinding.viewpager2.adapter = adminViewPagerAdapter
        activityAdminMainBinding.viewpager2.isUserInputEnabled = false
        activityAdminMainBinding.viewpager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        activityAdminMainBinding.bottomNavigation.menu.findItem(R.id.nav_admin_category).isChecked = true
                        activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_category)
                    }
                    1 -> {
                        activityAdminMainBinding.bottomNavigation.menu.findItem(R.id.nav_admin_food_drink).isChecked = true
                        activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_food_drink)
                    }
                    2 -> {
                        activityAdminMainBinding.bottomNavigation.menu.findItem(R.id.nav_admin_movie).isChecked = true
                        activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_movie)
                    }
                    3 -> {
                        activityAdminMainBinding.bottomNavigation.menu.findItem(R.id.nav_admin_booking).isChecked = true
                        activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_booking)
                    }
                    4 -> {
                        activityAdminMainBinding.bottomNavigation.menu.findItem(R.id.nav_admin_manage).isChecked = true
                        activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_manage)
                    }
                }
            }
        })
        activityAdminMainBinding.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_admin_category -> {
                    activityAdminMainBinding.viewpager2.currentItem = 0
                    activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_category)
                }
                R.id.nav_admin_food_drink -> {
                    activityAdminMainBinding.viewpager2.currentItem = 1
                    activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_food_drink)
                }
                R.id.nav_admin_movie -> {
                    activityAdminMainBinding.viewpager2.currentItem = 2
                    activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_movie)
                }
                R.id.nav_admin_booking -> {
                    activityAdminMainBinding.viewpager2.currentItem = 3
                    activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_booking)
                }
                R.id.nav_admin_manage -> {
                    activityAdminMainBinding.viewpager2.currentItem = 4
                    activityAdminMainBinding.tvTitle.text = getString(R.string.nav_admin_manage)
                }
            }
            true
        }
    }

    private fun showDialogLogout() {
        MaterialDialog(this)
            .title(res = R.string.app_name)
            .message(res = R.string.msg_confirm_login_another_device)
            .positiveButton(res = R.string.action_ok) { dialog ->
                dialog.dismiss()
                finishAffinity()
            }
            .negativeButton(res = R.string.action_cancel) { dialog ->
                dialog.dismiss()
            }
            .cancelable(true)
            .show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//        if (intentResult != null && intentResult.contents != null) {
//            EventBus.getDefault().post(ResultQrCodeEvent(intentResult.contents))
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        showDialogLogout()
    }
}