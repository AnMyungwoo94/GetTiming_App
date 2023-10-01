package com.myungwoo.gettiming_app.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.myungwoo.gettiming_app.background.RecentCoinWorkManager
import com.myungwoo.gettiming_app.databinding.ActivitySelectBinding
import com.myungwoo.gettiming_app.view.adapter.SelectRVAdapter
import com.myungwoo.gettiming_app.viewModel.SelectViewModel
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectBinding
    private val viewModel : SelectViewModel by viewModels()
    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinList()

        binding.laterTextArea.setOnClickListener {
            viewModel.setupFristFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, Observer {
            if(it.equals("done")){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                //가장 처음으로 우리가 불러온 코인의 정보가 저장되는 시점
                saveInterestCoinDataPeriodic()
            }
        })

        viewModel.currentPriceResult.observe(this, Observer {
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)

        })
    }

    private fun saveInterestCoinDataPeriodic(){

        val myWork = PeriodicWorkRequest.Builder(
            RecentCoinWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        //유니트하게 하나만 돌아가게끔,,
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "RecentCoinWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}