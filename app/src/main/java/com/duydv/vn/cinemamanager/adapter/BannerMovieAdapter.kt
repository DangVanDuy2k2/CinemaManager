package com.duydv.vn.cinemamanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.duydv.vn.cinemamanager.adapter.BannerMovieAdapter.BannerMovieViewHolder
import com.duydv.vn.cinemamanager.databinding.ItemBannerMovieBinding
import com.duydv.vn.cinemamanager.model.Movie
import com.duydv.vn.cinemamanager.util.GlideUtils

class BannerMovieAdapter(
    private val mListMovies: List<Movie>?,
    private val iClickItemListener: IClickItemListener
) : RecyclerView.Adapter<BannerMovieViewHolder>() {
    interface IClickItemListener {
        fun onClickItem(movie: Movie?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerMovieViewHolder {
        val itemBannerMovieBinding =
            ItemBannerMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerMovieViewHolder(itemBannerMovieBinding)
    }

    override fun onBindViewHolder(holder: BannerMovieViewHolder, position: Int) {
        val movie = mListMovies!![position]
        GlideUtils.loadUrlBanner(movie.image, holder.mItemBannerMovieBinding.imgBanner)
        holder.mItemBannerMovieBinding.tvTitle.text = movie.name
        holder.mItemBannerMovieBinding.tvBooked.text = movie.booked.toString()
        holder.mItemBannerMovieBinding.layoutItem.setOnClickListener {
            iClickItemListener.onClickItem(
                movie
            )
        }
    }

    override fun getItemCount(): Int {
        return mListMovies?.size ?: 0
    }

    class BannerMovieViewHolder(val mItemBannerMovieBinding: ItemBannerMovieBinding) :
        RecyclerView.ViewHolder(mItemBannerMovieBinding.root)
}