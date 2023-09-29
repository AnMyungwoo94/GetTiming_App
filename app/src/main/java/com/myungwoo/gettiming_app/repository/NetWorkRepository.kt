package com.myungwoo.gettiming_app.repository

import com.myungwoo.gettiming_app.network.Api
import com.myungwoo.gettiming_app.network.RetrofitInstance
import retrofit2.create

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)
    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
    suspend fun getInterestCoinPriceData(coin : String) = client.getRecentCoinPrice(coin)
}