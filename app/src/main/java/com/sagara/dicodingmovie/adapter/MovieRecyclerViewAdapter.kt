package com.sagara.dicodingmovie.adapter

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.base.BaseRecyclerViewAdapter
import com.sagara.dicodingmovie.service.response.Movie
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieRecyclerViewAdapter(
    private val listener: (item: Movie) -> Unit
): BaseRecyclerViewAdapter<Movie, MovieRecyclerViewAdapter.ViewHolder>() {

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(listener, view)
    }

    class ViewHolder(
        private val listener: (item: Movie) -> Unit,
        view: View
    ): RecyclerView.ViewHolder(view){

        fun bind(item: Movie){
            //! Set movie poster
            val uri = Uri.parse("https://image.tmdb.org/t/p/w92/${item.poster}")
            Glide.with(itemView.context)
                .load(uri)
                .into(itemView.i_movie_poster)

            //! Set other stuff
            itemView.i_movie_title.text = item.title
            itemView.i_movie_title.setTextColor(Color.BLACK)
            itemView.i_movie_overview.text = item.overview
            itemView.i_movie_overview.setTextColor(Color.BLACK)

            itemView.setOnClickListener { listener.invoke(item) }
        }
    }
}