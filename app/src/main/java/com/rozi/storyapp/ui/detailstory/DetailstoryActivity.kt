package com.rozi.storyapp.ui.detailstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.rozi.storyapp.R
import com.rozi.storyapp.data.remote.response.Story
import com.rozi.storyapp.databinding.ActivityDetailstoryBinding
import com.rozi.storyapp.utils.ViewModelFactory

class DetailstoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailstoryBinding
    private val detailstoryViewModel : DetailstoryViewModel by viewModels { ViewModelFactory.getInstance(application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailstoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_story)

        val title = intent.getStringExtra("title")

        detailstoryViewModel.getDetailStory(title!!)

        detailstoryViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailstoryViewModel.data.observe(this){
            setStory(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun setStory(story : Story){
        Glide.with(binding.root.context)
            .load(story.photoUrl)
            .into(binding.imageView)
        binding.tvName.text = story.name
        binding.tvDescrption.text = story.description
    }
}