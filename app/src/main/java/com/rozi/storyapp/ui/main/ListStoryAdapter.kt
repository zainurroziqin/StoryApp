package com.rozi.storyapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rozi.storyapp.data.remote.response.ListStoryItem
import com.rozi.storyapp.databinding.ItemStoryBinding

class ListStoryAdapter (private val listStory : List<ListStoryItem>) : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>(){

    class ListViewHolder(var binding : ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listStory: ListStoryItem){
            val name = listStory.name
            val description = listStory.description
            val image = listStory.photoUrl
            binding.tvItemName.text = name
            binding.tvItemDescription.text = description
            Glide.with(binding.root.context)
                .load(image)
                .into(binding.imgItemPhoto)
        }
    }

    private lateinit var onItemClickCallback : OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listStory[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listStory[holder.adapterPosition]) }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: ListStoryItem)
    }
}