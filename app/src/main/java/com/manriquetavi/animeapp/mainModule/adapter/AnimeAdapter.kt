package com.manriquetavi.animeapp.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.manriquetavi.animeapp.R
import com.manriquetavi.animeapp.databinding.ItemAnimeBinding
import com.manriquetavi.animeapp.common.entities.AnimeEntity

class AnimeAdapter(private var animes: MutableList<AnimeEntity>, private var listener: OnClickListener):
    RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

        private lateinit var mContext: Context

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val binding = ItemAnimeBinding.bind(view)

            fun setListener(animeEntity: AnimeEntity){
                binding.root.setOnClickListener{ listener.onClick(animeEntity)}
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_anime, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anime = animes[position]

        with(holder){
            setListener(anime)
            binding.tvNameAnime.text = anime.title
            Glide.with(mContext)
                .load(anime.coverImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }

    override fun getItemCount(): Int {
        return animes.size
    }

    fun setAnimes(animes: MutableList<AnimeEntity>){
        this.animes = animes
        notifyDataSetChanged()
    }
}