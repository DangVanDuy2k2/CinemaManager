package com.duydv.vn.cinemamanager.activitys

import android.os.Bundle
import android.widget.Toast
import com.duydv.vn.cinemamanager.R
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.constant.GlobalFunction
import com.duydv.vn.cinemamanager.databinding.ActivitySignUpBinding
import com.duydv.vn.cinemamanager.prefs.DataStoreManager
import com.duydv.vn.cinemamanager.util.StringUtil
import com.duydv.vn.cinemamanager.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : BaseActivity() {

    private var mActivitySignUpBinding: ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(mActivitySignUpBinding!!.root)
        mActivitySignUpBinding!!.imgBack.setOnClickListener { onBackPressed() }
        mActivitySignUpBinding!!.layoutSignIn.setOnClickListener { finish() }
        mActivitySignUpBinding!!.btnSignUp.setOnClickListener { onClickValidateSignUp() }
    }

    private fun onClickValidateSignUp() {
        val strEmail = mActivitySignUpBinding!!.edtEmail.text.toString().trim { it <= ' ' }
        val strPassword = mActivitySignUpBinding!!.edtPassword.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.msg_email_require),
                Toast.LENGTH_SHORT
            ).show()
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.msg_password_require),
                Toast.LENGTH_SHORT
            ).show()
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.msg_email_invalid),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            signUpUser(strEmail, strPassword)
        }
    }

    private fun signUpUser(email: String, password: String) {
        showProgressDialog(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                showProgressDialog(false)
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        val userObject = User(user.email, password)
                        if (user.email != null && user.email!!.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                            userObject.isAdmin = true
                        }
                        DataStoreManager.setUser(userObject)
                        GlobalFunction.gotoMainActivity(this)
                        finishAffinity()
                    }
                } else {
                    Toast.makeText(
                        this@SignUpActivity, getString(R.string.msg_sign_up_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}