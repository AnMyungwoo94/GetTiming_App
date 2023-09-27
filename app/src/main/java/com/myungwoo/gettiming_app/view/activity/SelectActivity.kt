package com.myungwoo.gettiming_app.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.myungwoo.gettiming_app.databinding.ActivitySelectBinding
import com.myungwoo.gettiming_app.view.adapter.SelectRVAdapter
import com.myungwoo.gettiming_app.viewModel.SelectViewModel

class SelectActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectBinding
    private val viewModel : SelectViewModel by viewModels()
    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinLisr()

        binding.laterTextArea.setOnClickListener {
            viewModel.setupFristFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, Observer {
            if(it.equals("done")){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })

        viewModel.currentPriceResult.observe(this, Observer {
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)

        })


    }
}