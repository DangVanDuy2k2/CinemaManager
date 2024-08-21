package com.duydv.vn.cinemamanager.activitys

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.duydv.vn.cinemamanager.R

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: MaterialDialog? = null
    private var alertDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createProgressDialog()
        createAlertDialog()
    }

    private fun createProgressDialog() {
        progressDialog = MaterialDialog(this)
            .noAutoDismiss()
            .title(text = "Loading")
            .message(text = getString(R.string.waiting_message))
            .cancelOnTouchOutside(false)
    }

    fun showProgressDialog(value: Boolean) {
        if (value) {
            if (progressDialog != null && !progressDialog!!.isShowing) {
                progressDialog!!.show()
                progressDialog!!.setCancelable(false)
            }
        } else {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
    }

    private fun createAlertDialog() {
        alertDialog = MaterialDialog(this)
            .title(text = resources.getString(R.string.app_name))
            .positiveButton(text = resources.getString(R.string.action_ok))
            .cancelOnTouchOutside(false)
    }

    fun showAlertDialog(errorMessage: String?) {
        alertDialog!!.setTitle(errorMessage)
        alertDialog!!.show()
    }

    fun showAlertDialog(@StringRes resourceId: Int) {
        alertDialog!!.setTitle(resourceId)
        alertDialog!!.show()
    }

    fun setCancelProgress(isCancel: Boolean) {
        if (progressDialog != null) {
            progressDialog!!.setCancelable(isCancel)
        }
    }

    override fun onDestroy() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
        super.onDestroy()
    }
}