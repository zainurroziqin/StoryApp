package com.rozi.storyapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.rozi.storyapp.R
import com.rozi.storyapp.databinding.ActivityRegisterBinding
import com.rozi.storyapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val name : String = binding.edtNama.text.toString()
            val email : String = binding.edtEmail.text.toString()
            val password : String = binding.edtPassword.text.toString()
            registerViewModel.register(name, email, password)
        }

        registerViewModel.isLoading.observe(this){
            showLoading(it)
        }

        registerViewModel.successLogin.observe(this){
            register(it)
        }
    }

    private fun register(success : Boolean){
        if (success){
            Toast.makeText(this, getString(R.string.register_berhasil), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            Toast.makeText(this, getString(R.string.register_gagal), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}