package com.rozi.storyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rozi.storyapp.R
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.remote.response.DetailStoryResponse
import com.rozi.storyapp.data.remote.response.ListStoryItem
import com.rozi.storyapp.databinding.ActivityMainBinding
import com.rozi.storyapp.ui.addstory.AddstoryActivity
import com.rozi.storyapp.ui.detailstory.DetailstoryActivity
import com.rozi.storyapp.ui.login.LoginActivity
import com.rozi.storyapp.ui.maps.MapsActivity
import com.rozi.storyapp.utils.LoadingStateAdapter
import com.rozi.storyapp.utils.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(
            application
        )
    }
    private val LAUNCH_SECOND_ACTIVITY = 1
    private lateinit var mTokenPreferences : TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager
        getStory()
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvStories.addItemDecoration(itemDecoration)

        mTokenPreferences = TokenPreferences(this)

        binding.addStory.setOnClickListener {
            val intent = Intent(this@MainActivity, AddstoryActivity::class.java)
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)

        }

    }

    private fun getStory() {
        val adapter = StoryListAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.story.observe(this){
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_logout -> {
                logout()
            }
            R.id.action_pengaturan ->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.action_maps -> {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        mTokenPreferences.setToken("")
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                getStory()
            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    private fun setListStory(listStory: List<ListStoryItem>) {
        val adapter = ListStoryAdapter(listStory)
        binding.rvStories.adapter = adapter
        adapter.setOnItemClickCallback(object  : ListStoryAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedCountry(data)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun showError(error: Boolean) {
        if (error) {
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.tvError.visibility = View.INVISIBLE
        }
    }

    private fun showSelectedCountry(story : ListStoryItem){
        val intentDetail = Intent( this@MainActivity, DetailstoryActivity::class.java)
        intentDetail.putExtra("title", story.id)
        startActivity(intentDetail)
    }
}