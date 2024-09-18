package com.duydv.vn.cinemamanager.activitys.admin

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.duydv.vn.cinemamanager.MyApplication
import com.duydv.vn.cinemamanager.R
import com.duydv.vn.cinemamanager.activitys.BaseActivity
import com.duydv.vn.cinemamanager.adapter.admin.AdminSelectCategoryAdapter
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.constant.GlobalFunction
import com.duydv.vn.cinemamanager.databinding.ActivityAddMovieBinding
import com.duydv.vn.cinemamanager.model.Category
import com.duydv.vn.cinemamanager.model.Movie
import com.duydv.vn.cinemamanager.util.StringUtil
import com.example.cinema.listener.IGetDateListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*

class AddMovieActivity : BaseActivity() {

    private var mActivityAddMovieBinding: ActivityAddMovieBinding? = null
    private var isUpdate = false
    private var mMovie: Movie? = null
    private var mListCategory: MutableList<Category>? = null
    private var mCategorySelected: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityAddMovieBinding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(mActivityAddMovieBinding!!.root)
        val bundleReceived = intent.extras
        if (bundleReceived != null) {
            isUpdate = true
            mMovie = bundleReceived[Constant.KEY_INTENT_MOVIE_OBJECT] as Movie?
        }
        initView()
        getListCategory()
        mActivityAddMovieBinding!!.imgBack.setOnClickListener { onBackPressed() }
        mActivityAddMovieBinding!!.btnAddOrEdit.setOnClickListener { addOrEditMovie() }
        mActivityAddMovieBinding!!.tvDate.setOnClickListener {
            if (isUpdate) {
                GlobalFunction.showDatePicker(this@AddMovieActivity, mMovie?.date,
                    object : IGetDateListener{
                        override fun getDate(date: String?) {
                            mActivityAddMovieBinding!!.tvDate.text = date
                        }
                    })
            } else {
                GlobalFunction.showDatePicker(this@AddMovieActivity, "",
                    object : IGetDateListener {
                        override fun getDate(date: String?) {
                            mActivityAddMovieBinding!!.tvDate.text = date
                        }
                    })
            }
        }
    }

    private fun initView() {
        if (isUpdate) {
            mActivityAddMovieBinding!!.tvTitle.text = getString(R.string.edit_movie_title)
            mActivityAddMovieBinding!!.btnAddOrEdit.text = getString(R.string.action_edit)
            mActivityAddMovieBinding!!.edtName.setText(mMovie?.name)
            mActivityAddMovieBinding!!.edtDescription.setText(mMovie?.description)
            mActivityAddMovieBinding!!.edtPrice.setText(mMovie?.price.toString())
            mActivityAddMovieBinding!!.tvDate.text = mMovie?.date
            mActivityAddMovieBinding!!.edtImage.setText(mMovie?.image)
            mActivityAddMovieBinding!!.edtBanner.setText(mMovie?.banner)
        } else {
            mActivityAddMovieBinding!!.tvTitle.text = getString(R.string.add_movie_title)
            mActivityAddMovieBinding!!.btnAddOrEdit.text = getString(R.string.action_add)
        }
    }

    private fun getListCategory() {
        MyApplication[this].getCategoryDatabaseReference()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (mListCategory != null) {
                        mListCategory!!.clear()
                    } else {
                        mListCategory = ArrayList()
                    }
                    for (dataSnapshot in snapshot.children) {
                        val category = dataSnapshot.getValue(Category::class.java)!!
                        mListCategory!!.add(0, category)
                    }
                    initSpinnerCategory()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun getPositionCategoryUpdate(movie: Movie?): Int {
        if (mListCategory == null || mListCategory!!.isEmpty()) {
            return 0
        }
        for (i in mListCategory!!.indices) {
            if (movie?.categoryId == mListCategory!![i].id) {
                return i
            }
        }
        return 0
    }

    private fun initSpinnerCategory() {
        val selectCategoryAdapter = AdminSelectCategoryAdapter(this,
            R.layout.item_choose_option, mListCategory!!)
        mActivityAddMovieBinding!!.spnCategory.adapter = selectCategoryAdapter
        mActivityAddMovieBinding!!.spnCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                mCategorySelected = selectCategoryAdapter.getItem(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        if (isUpdate) {
            mActivityAddMovieBinding!!.spnCategory.setSelection(getPositionCategoryUpdate(mMovie))
        }
    }

    private fun addOrEditMovie() {
        val strName = mActivityAddMovieBinding!!.edtName.text.toString().trim { it <= ' ' }
        val strDescription = mActivityAddMovieBinding!!.edtDescription.text.toString().trim { it <= ' ' }
        val strPrice = mActivityAddMovieBinding!!.edtPrice.text.toString().trim { it <= ' ' }
        val strDate = mActivityAddMovieBinding!!.tvDate.text.toString().trim { it <= ' ' }
        val strImage = mActivityAddMovieBinding!!.edtImage.text.toString().trim { it <= ' ' }
        val strImageBanner = mActivityAddMovieBinding!!.edtBanner.text.toString().trim { it <= ' ' }
        if (mCategorySelected == null || mCategorySelected!!.id <= 0) {
            Toast.makeText(this, getString(R.string.msg_category_movie_require), Toast.LENGTH_SHORT).show()
            return
        }
        if (StringUtil.isEmpty(strName)) {
            Toast.makeText(this, getString(R.string.msg_name_movie_require), Toast.LENGTH_SHORT).show()
            return
        }
        if (StringUtil.isEmpty(strDescription)) {
            Toast.makeText(this, getString(R.string.msg_description_movie_require), Toast.LENGTH_SHORT).show()
            return
        }
        if (StringUtil.isEmpty(strPrice)) {
            Toast.makeText(this, getString(R.string.msg_price_movie_require), Toast.LENGTH_SHORT).show()
            return
        }
        if (StringUtil.isEmpty(strDate)) {
            Toast.makeText(this, getString(R.string.msg_date_movie_require), Toast.LENGTH_SHORT).show()
            return
        }
        if (StringUtil.isEmpty(strImage)) {
            Toast.makeText(this, getString(R.string.msg_image_movie_require), Toast.LENGTH_SHORT).show()
            return
        }
        if (StringUtil.isEmpty(strImageBanner)) {
            Toast.makeText(this, getString(R.string.msg_image_banner_movie_require), Toast.LENGTH_SHORT).show()
            return
        }

        // Update phim
        if (isUpdate) {
            showProgressDialog(true)
            val map: MutableMap<String, Any?> = HashMap()
            map["name"] = strName
            map["description"] = strDescription
            map["price"] = strPrice.toInt()
            if (strDate != mMovie?.date) {
                map["date"] = strDate
                map["rooms"] = GlobalFunction.getListRooms()
            }
            map["image"] = strImage
            map["categoryId"] = mCategorySelected?.id
            map["categoryName"] = mCategorySelected?.name
            MyApplication[this].getMovieDatabaseReference()
                .child(mMovie?.id.toString())
                .updateChildren(map) { _: DatabaseError?, _: DatabaseReference? ->
                    showProgressDialog(false)
                    Toast.makeText(this@AddMovieActivity,
                        getString(R.string.msg_edit_movie_successfully), Toast.LENGTH_SHORT).show()
                    GlobalFunction.hideSoftKeyboard(this)
                }
            onBackPressed()
            return
        }

        // Add phim
        showProgressDialog(true)
        val movieId = System.currentTimeMillis()
        val movie = Movie(movieId, strName, strDescription, strPrice.toInt(),
            strDate, strImage, strImageBanner, GlobalFunction.getListRooms(),
            mCategorySelected!!.id, mCategorySelected?.name, 0)
        MyApplication[this].getMovieDatabaseReference().child(movieId.toString())
            .setValue(movie) { _: DatabaseError?, _: DatabaseReference? ->
                showProgressDialog(false)
                mActivityAddMovieBinding!!.spnCategory.setSelection(0)
                mActivityAddMovieBinding!!.edtName.setText("")
                mActivityAddMovieBinding!!.edtDescription.setText("")
                mActivityAddMovieBinding!!.edtPrice.setText("")
                mActivityAddMovieBinding!!.tvDate.text = ""
                mActivityAddMovieBinding!!.edtImage.setText("")
                mActivityAddMovieBinding!!.edtBanner.setText("")
                GlobalFunction.hideSoftKeyboard(this)
                Toast.makeText(this, getString(R.string.msg_add_movie_successfully),
                    Toast.LENGTH_SHORT).show()
            }
        onBackPressed()
    }
}