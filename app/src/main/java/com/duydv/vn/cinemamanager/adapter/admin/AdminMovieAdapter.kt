package com.duydv.vn.cinemamanager.adapter.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duydv.vn.cinemamanager.constant.Constant
import com.duydv.vn.cinemamanager.databinding.ItemMovieAdminBinding
import com.duydv.vn.cinemamanager.util.GlideUtils
import com.example.cinema.model.Movie

class AdminMovieAdapter(
    private var mContext: Context?, private val mListMovies: List<Movie>?,
    private val iManagerMovieListener: IManagerMovieListener
) : RecyclerView.Adapter<AdminMovieAdapter.MovieViewHolder>() {
    interface IManagerMovieListener {
        fun editMovie(movie: Movie)
        fun deleteMovie(movie: Movie)
        fun clickItemMovie(movie: Movie?)
    }

    fun release() {
        if (mContext != null) {
            mContext = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieAdminBinding =
            ItemMovieAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieAdminBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = mListMovies!![position]
        GlideUtils.loadUrl(movie.image, holder.mItemMovieAdminBinding.imgMovie)
        holder.mItemMovieAdminBinding.tvName.text = movie.name
        holder.mItemMovieAdminBinding.tvCategory.text = movie.categoryName
        holder.mItemMovieAdminBinding.tvDescription.text = movie.description
        val strPrice = movie.price.toString() + Constant.UNIT_CURRENCY_MOVIE
        holder.mItemMovieAdminBinding.tvPrice.text = strPrice
        holder.mItemMovieAdminBinding.tvDate.text = movie.date
        holder.mItemMovieAdminBinding.imgEdit.setOnClickListener {
            iManagerMovieListener.editMovie(
                movie
            )
        }
        holder.mItemMovieAdminBinding.imgDelete.setOnClickListener {
            iManagerMovieListener.deleteMovie(
                movie
            )
        }
        holder.mItemMovieAdminBinding.layoutItem.setOnClickListener {
            iManagerMovieListener.clickItemMovie(
                movie
            )
        }
    }

    override fun getItemCount(): Int {
        return mListMovies?.size ?: 0
    }

    class MovieViewHolder(val mItemMovieAdminBinding: ItemMovieAdminBinding) :
        RecyclerView.ViewHolder(mItemMovieAdminBinding.root)
}