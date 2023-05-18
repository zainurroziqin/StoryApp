package com.rozi.storyapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rozi.storyapp.R
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.lokal.database.StoryEntity
import com.rozi.storyapp.databinding.ActivityMainBinding
import com.rozi.storyapp.ui.addstory.AddstoryActivity
import com.rozi.storyapp.ui.detailstory.DetailstoryActivity
import com.rozi.storyapp.ui.login.LoginActivity
import com.rozi.storyapp.ui.maps.MapsActivity
import com.rozi.storyapp.utils.LoadingStateAdapter
import com.rozi.storyapp.utils.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryListAdapter
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

        mTokenPreferences = TokenPreferences(this)

        binding.addStory.setOnClickListener {
            val intent = Intent(this@MainActivity, AddstoryActivity::class.java)
            startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY)

        }


    }

    private fun getStory() {
        adapter = StoryListAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.story.observe(this){
            adapter.submitData(lifecycle, it)
        }
        adapter.setOnItemClickCallback(object : StoryListAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: StoryEntity) {
                showSelectedStory(data)
            }

        })

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
        }
    }

    private fun showSelectedStory(story : StoryEntity){
        val intentDetail = Intent( this@MainActivity, DetailstoryActivity::class.java)
        intentDetail.putExtra("title", story.id)
        startActivity(intentDetail)
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }
}