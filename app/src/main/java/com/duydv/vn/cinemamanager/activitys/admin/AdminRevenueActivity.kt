package com.duydv.vn.cinemamanager.activitys.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.duydv.vn.cinemamanager.MyApplication
import com.duydv.vn.cinemamanager.adapter.admin.AdminRevenueAdapter
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.databinding.ActivityAdminRevenueBinding
import com.duydv.vn.cinemamanager.model.BookingHistory
import com.duydv.vn.cinemamanager.model.Revenue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.Collections

class AdminRevenueActivity : AppCompatActivity() {

    private var mActivityAdminRevenueBinding: ActivityAdminRevenueBinding? = null
    private var mListRevenue: MutableList<Revenue>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityAdminRevenueBinding = ActivityAdminRevenueBinding.inflate(layoutInflater)
        setContentView(mActivityAdminRevenueBinding!!.root)
        initListener()
        getListRevenue()
    }

    private fun initListener() {
        mActivityAdminRevenueBinding!!.imgBack.setOnClickListener { onBackPressed() }
    }

    private fun getListRevenue() {
        MyApplication[this].getBookingDatabaseReference()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list: MutableList<BookingHistory> = ArrayList()
                    for (dataSnapshot in snapshot.children) {
                        val bookingHistory = dataSnapshot.getValue(BookingHistory::class.java)
                        if (bookingHistory != null) {
                            list.add(bookingHistory)
                        }
                    }
                    handleDataHistories(list)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun handleDataHistories(list: List<BookingHistory>?) {
        if (list == null || list.isEmpty()) {
            return
        }
        if (mListRevenue != null) {
            mListRevenue!!.clear()
        } else {
            mListRevenue = ArrayList()
        }
        for (history in list) {
            val movieId = history.movieId
            if (checkRevenueExist(movieId)) {
                getRevenueFromMovieId(movieId)!!.getHistories().add(history)
            } else {
                val revenue = Revenue()
                revenue.movieId = history.movieId
                revenue.movieName = history.name
                revenue.getHistories().add(history)
                mListRevenue!!.add(revenue)
            }
        }
        val linearLayoutManager = LinearLayoutManager(this)
        mActivityAdminRevenueBinding!!.rcvData.layoutManager = linearLayoutManager
        val listFinal: List<Revenue> = ArrayList(mListRevenue!!)
        Collections.sort(listFinal) { statistical1: Revenue, statistical2: Revenue
            ->
            statistical2.totalPrice - statistical1.totalPrice
        }
        val adminRevenueAdapter = AdminRevenueAdapter(listFinal)
        mActivityAdminRevenueBinding!!.rcvData.adapter = adminRevenueAdapter

        // Calculate total
        val strTotalValue = getTotalValues().toString() + Constant.UNIT_CURRENCY
        mActivityAdminRevenueBinding!!.tvTotalValue.text = strTotalValue
    }

    private fun checkRevenueExist(movieId: Long): Boolean {
        if (mListRevenue == null || mListRevenue!!.isEmpty()) {
            return false
        }
        var result = false
        for (revenue in mListRevenue!!) {
            if (movieId == revenue.movieId) {
                result = true
                break
            }
        }
        return result
    }

    private fun getRevenueFromMovieId(movieId: Long): Revenue? {
        var result: Revenue? = null
        for (revenue in mListRevenue!!) {
            if (movieId == revenue.movieId) {
                result = revenue
                break
            }
        }
        return result
    }

    private fun getTotalValues(): Int {
        if (mListRevenue == null || mListRevenue!!.isEmpty()) {
            return 0
        }
        var total = 0
        for (revenue in mListRevenue!!) {
            total += revenue.totalPrice
        }
        return total
    }
}