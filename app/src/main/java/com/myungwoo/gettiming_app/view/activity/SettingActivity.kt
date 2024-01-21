package com.myungwoo.gettiming_app.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.myungwoo.gettiming_app.R
import com.myungwoo.gettiming_app.databinding.ActivitySettingBinding
import com.myungwoo.gettiming_app.sevice.PriceForegroundService

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val isTiramisuOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

        var hasNotificationPermission =
            if (isTiramisuOrHigher)
                ContextCompat.checkSelfPermission(
                    this,
                    notificationPermission
                ) == PackageManager.PERMISSION_GRANTED
            else true

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            hasNotificationPermission = it
            if (it) {
                val intent = Intent(this, PriceForegroundService::class.java)
                intent.action = "START"
                startService(intent)
            }
        }

        binding.startForeground.setOnClickListener {

            Toast.makeText(this, "start", Toast.LENGTH_LONG).show()

            if (hasNotificationPermission) {
                val intent = Intent(this, PriceForegroundService::class.java)
                intent.action = "START"
                startService(intent)
            } else if (isTiramisuOrHigher) {
                launcher.launch(notificationPermission)
            }

        }

        binding.stopForeground.setOnClickListener {

            Toast.makeText(this, "stop", Toast.LENGTH_LONG).show()

            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "STOP"
            startService(intent)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}