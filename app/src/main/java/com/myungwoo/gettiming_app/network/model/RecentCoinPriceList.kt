package com.myungwoo.gettiming_app.network.model

import com.myungwoo.gettiming_app.dataModel.RecentPriceData

data class RecentCoinPriceList(
    val status: String,
    val data: List<RecentPriceData>
)