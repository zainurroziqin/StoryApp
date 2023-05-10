package com.rozi.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.rozi.storyapp.R
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.databinding.ActivityLoginBinding
import com.rozi.storyapp.ui.main.MainActivity
import com.rozi.storyapp.ui.register.RegisterActivity
import com.rozi.storyapp.utils.ViewModelFactory
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels{ ViewModelFactory.getInstance(application) }
    private lateinit var mTokenPreferences: TokenPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mTokenPreferences = TokenPreferences(this)
        checkLogin()


        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email : String = binding.edtEmail.text.toString()
            val password : String = binding.edtPassword.text.toString()
            if(emailValidator(email) && password.length >=8 ){
                loginViewModel.login(email, password)
            }
        }
        loginViewModel.successLogin.observe(this) {
            login(it)
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        playAnimation()
    }

    private fun checkLogin() {
        val token = mTokenPreferences.getToken()
        if(token != ""){
            val intent  = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(success: Boolean) {
        if (success) {
            Toast.makeText(this, getString(R.string.login_berhasil), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.Login_gagal), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun emailValidator(email : String) : Boolean{
        val pattern : Pattern
        val matcher : Matcher
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        pattern = Pattern.compile(emailPattern)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30F, 30F).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailTitle = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTitle = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA,1f).setDuration(500)

        val emailAnimation = AnimatorSet().apply {
            playTogether(emailTitle, email)
        }

        val passwordAnimation = AnimatorSet().apply {
            playTogether(password, passwordTitle)
        }

        AnimatorSet().apply {
            playSequentially(emailAnimation, passwordAnimation, login, register)
            startDelay = 500
            start()
        }
    }
}