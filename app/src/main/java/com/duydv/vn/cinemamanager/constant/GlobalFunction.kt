package com.duydv.vn.cinemamanager.constant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.duydv.vn.cinemamanager.activitys.MainActivity
import com.duydv.vn.cinemamanager.activitys.admin.AdminMainActivity
import com.duydv.vn.cinemamanager.prefs.DataStoreManager
import java.text.Normalizer
import java.util.regex.Pattern

object GlobalFunction {
    fun startActivity(context: Context?, clz: Class<*>?) {
        val intent = Intent(context, clz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(intent)
    }

    fun startActivity(context: Context?, clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle!!)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(intent)
    }

    fun getTextSearch(input: String?): String {
        val nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(nfdNormalizedString).replaceAll("")
    }

    fun hideSoftKeyboard(activity: Activity?) {
        try {
            val inputMethodManager =
                activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        }
    }

    fun gotoMainActivity(context: Context?) {
        if (DataStoreManager.getUser()!!.isAdmin) {
            startActivity(context, AdminMainActivity::class.java)
        } else {
            startActivity(context, MainActivity::class.java)
        }
    }
}