package com.myungwoo.gettiming_app.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myungwoo.gettiming_app.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.tag("MainAvtivity").d("onCreate")
    }
}