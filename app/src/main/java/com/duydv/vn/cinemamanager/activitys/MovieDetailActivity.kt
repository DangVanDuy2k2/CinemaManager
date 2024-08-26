package com.duydv.vn.cinemamanager.activitys

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.duydv.vn.cinemamanager.activitys.admin.ConfirmBookingActivity
import com.duydv.vn.cinemamanager.MyApplication
import com.duydv.vn.cinemamanager.R
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.constant.GlobalFunction
import com.duydv.vn.cinemamanager.databinding.ActivityMovieDetailBinding
import com.duydv.vn.cinemamanager.model.Movie
import com.duydv.vn.cinemamanager.util.DateTimeUtils
import com.duydv.vn.cinemamanager.util.GlideUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MovieDetailActivity : AppCompatActivity() {

    private var mActivityMovieDetailBinding: ActivityMovieDetailBinding? = null
    private var mMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMovieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(mActivityMovieDetailBinding!!.root)
        getDataIntent()
    }

    private fun getDataIntent() {
        val bundle = intent.extras ?: return
        val movie = bundle[Constant.KEY_INTENT_MOVIE_OBJECT] as Movie?
        getMovieInformation(movie!!.id)
    }

    private fun getMovieInformation(movieId: Long) {
        MyApplication[this].getMovieDatabaseReference().child(movieId.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mMovie = snapshot.getValue(Movie::class.java)
                    displayDataMovie()
                    initListener()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun displayDataMovie() {
        if (mMovie == null) {
            return
        }
        GlideUtils.loadUrl(mMovie?.image, mActivityMovieDetailBinding!!.imgMovie)
        mActivityMovieDetailBinding!!.tvTitleMovie.text = mMovie?.name
        mActivityMovieDetailBinding!!.tvCategoryName.text = mMovie?.categoryName
        mActivityMovieDetailBinding!!.tvDateMovie.text = mMovie?.date
        val strPrice = mMovie?.price.toString() + Constant.UNIT_CURRENCY_MOVIE
        mActivityMovieDetailBinding!!.tvPriceMovie.text = strPrice
        mActivityMovieDetailBinding!!.tvDescriptionMovie.text = mMovie?.description
    }

    private fun initListener() {
        mActivityMovieDetailBinding!!.imgBack.setOnClickListener { onBackPressed() }
        mActivityMovieDetailBinding!!.btnBooking.setOnClickListener { onClickGoToConfirmBooking() }
    }

    private fun onClickGoToConfirmBooking() {
        if (mMovie == null) {
            return
        }
        if (DateTimeUtils.convertDateToTimeStamp(mMovie?.date) < DateTimeUtils.getLongCurrentTimeStamp()) {
            Toast.makeText(this, getString(R.string.msg_movie_date_invalid), Toast.LENGTH_SHORT)
                .show()
            return
        }
        val bundle = Bundle()
        bundle.putSerializable(Constant.KEY_INTENT_MOVIE_OBJECT, mMovie)
        GlobalFunction.startActivity(this, ConfirmBookingActivity::class.java, bundle)
    }
}