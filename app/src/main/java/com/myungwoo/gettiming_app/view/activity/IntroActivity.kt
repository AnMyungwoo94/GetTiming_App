package com.myungwoo.gettiming_app.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.myungwoo.gettiming_app.R
import com.myungwoo.gettiming_app.databinding.ActivityIntroBinding
import com.myungwoo.gettiming_app.viewModel.IntroViewModel
import timber.log.Timber

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private val viewModel: IntroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkFirstFlag()
        viewModel.frist.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.animationView.visibility = View.INVISIBLE
                binding.fragmentContainerView.visibility = View.VISIBLE
            }
        })
        Timber.d("IntroActivity")
    }
}