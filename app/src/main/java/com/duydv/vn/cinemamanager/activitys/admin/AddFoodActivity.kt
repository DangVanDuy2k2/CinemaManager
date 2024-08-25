package com.duydv.vn.cinemamanager.activitys.admin

import android.os.Bundle
import android.widget.Toast
import com.duydv.vn.cinemamanager.MyApplication
import com.duydv.vn.cinemamanager.R
import com.duydv.vn.cinemamanager.activitys.BaseActivity
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.constant.GlobalFunction
import com.duydv.vn.cinemamanager.databinding.ActivityAddFoodBinding
import com.duydv.vn.cinemamanager.model.Food
import com.duydv.vn.cinemamanager.util.StringUtil
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class AddFoodActivity : BaseActivity() {

    private var mActivityAddFoodBinding: ActivityAddFoodBinding? = null
    private var isUpdate = false
    private var mFood: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityAddFoodBinding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(mActivityAddFoodBinding!!.root)
        val bundleReceived = intent.extras
        if (bundleReceived != null) {
            isUpdate = true
            mFood = bundleReceived[Constant.KEY_INTENT_FOOD_OBJECT] as Food?
        }
        initView()
        mActivityAddFoodBinding!!.imgBack.setOnClickListener { onBackPressed() }
        mActivityAddFoodBinding!!.btnAddOrEdit.setOnClickListener { addOrEditFood() }
    }

    private fun initView() {
        if (isUpdate) {
            mActivityAddFoodBinding!!.tvTitle.text = getString(R.string.edit_food_title)
            mActivityAddFoodBinding!!.btnAddOrEdit.text = getString(R.string.action_edit)
            mActivityAddFoodBinding!!.edtName.setText(mFood?.name)
            mActivityAddFoodBinding!!.edtPrice.setText(mFood?.price.toString())
            mActivityAddFoodBinding!!.edtQuantity.setText(mFood?.quantity.toString())
            mActivityAddFoodBinding!!.edtImage.setText(mFood?.image.toString())
        } else {
            mActivityAddFoodBinding!!.tvTitle.text = getString(R.string.add_food_title)
            mActivityAddFoodBinding!!.btnAddOrEdit.text = getString(R.string.action_add)
        }
    }

    private fun addOrEditFood() {
        val strName = mActivityAddFoodBinding!!.edtName.text.toString().trim { it <= ' ' }
        val strPrice = mActivityAddFoodBinding!!.edtPrice.text.toString().trim { it <= ' ' }
        val strQuantity = mActivityAddFoodBinding!!.edtQuantity.text.toString().trim { it <= ' ' }
        val strImage = mActivityAddFoodBinding!!.edtImage.text.toString().trim { it <= ' ' }
        if (StringUtil.isEmpty(strName)) {
            Toast.makeText(this, getString(R.string.msg_name_food_require), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (StringUtil.isEmpty(strPrice)) {
            Toast.makeText(this, getString(R.string.msg_price_food_require), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (StringUtil.isEmpty(strQuantity)) {
            Toast.makeText(this, getString(R.string.msg_quantity_food_require), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (StringUtil.isEmpty(strImage)) {
            Toast.makeText(this, getString(R.string.msg_image_food_require), Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Update food
        if (isUpdate) {
            showProgressDialog(true)
            val map: MutableMap<String, Any> = HashMap()
            map["name"] = strName
            map["price"] = strPrice.toInt()
            map["quantity"] = strQuantity.toInt()
            MyApplication[this].getFoodDatabaseReference()
                .child(mFood?.id.toString())
                .updateChildren(map) { _: DatabaseError?, _: DatabaseReference? ->
                    showProgressDialog(false)
                    Toast.makeText(
                        this@AddFoodActivity,
                        getString(R.string.msg_edit_food_successfully), Toast.LENGTH_SHORT
                    ).show()
                    GlobalFunction.hideSoftKeyboard(this@AddFoodActivity)
                }
            onBackPressed()
            return
        }

        // Add food
        showProgressDialog(true)
        val foodId = System.currentTimeMillis()
        val food = Food(foodId, strName, strImage, strPrice.toInt(), strQuantity.toInt())
        MyApplication[this].getFoodDatabaseReference().child(foodId.toString())
            .setValue(food) { _: DatabaseError?, _: DatabaseReference? ->
                showProgressDialog(false)
                mActivityAddFoodBinding!!.edtName.setText("")
                mActivityAddFoodBinding!!.edtPrice.setText("")
                mActivityAddFoodBinding!!.edtQuantity.setText("")
                GlobalFunction.hideSoftKeyboard(this)
                Toast.makeText(
                    this, getString(R.string.msg_add_food_successfully),
                    Toast.LENGTH_SHORT
                ).show()
            }
        onBackPressed()
    }
}