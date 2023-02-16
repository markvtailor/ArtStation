package com.markvtls.artstation.presentation.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.markvtls.artstation.databinding.FragmentFavoritesItemBinding
import com.markvtls.artstation.domain.model.Image

class FavoritesAdapter(
    private val context: Context,
    private val delete: (String) -> Unit
    ): ListAdapter<Image, FavoritesAdapter.FavoritesViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesViewHolder {
       return FavoritesViewHolder(
           FragmentFavoritesItemBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           ),
           context,
           delete
       )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class FavoritesViewHolder(
        private val binding: FragmentFavoritesItemBinding,
        private val context: Context,
        private val delete: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {

            Glide.with(context)
                .load(image.url.toUri())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.image)

            binding.description.text = image.title

            binding.deleteFromFavorites.setOnClickListener {
                delete(image.id)
            }
        }
    }


    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}