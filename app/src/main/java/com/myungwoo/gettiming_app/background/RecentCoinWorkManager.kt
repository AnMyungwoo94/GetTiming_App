package com.myungwoo.gettiming_app.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.myungwoo.gettiming_app.db.entity.SelectedCoinPriceEntity
import com.myungwoo.gettiming_app.network.model.RecentCoinPriceList
import com.myungwoo.gettiming_app.repository.DBRepository
import com.myungwoo.gettiming_app.repository.NetWorkRepository
import timber.log.Timber
import java.util.*

//최근 거래된 코인 가격 내역을 가져오는 WorkManager
class RecentCoinWorkManager(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()
    private val networkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {

        Timber.d("dowork")
        getAllInterestSelectedCoinData()

        return Result.success()
    }

    //1. 관심있는 코인 리스트를 가져오기
    suspend fun getAllInterestSelectedCoinData() {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()
        val timeStamp = Calendar.getInstance().time

        for (coinData in selectedCoinList) {
            Timber.d(selectedCoinList.toString())


            //2. 관심있는 코인 각각의 가격 변동 정보를 가져온다.
            val recentCoinPriceData = networkRepository.getInterestCoinPriceData(coinData.coin_name)
            Timber.d(recentCoinPriceData.toString())

            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPriceData,
                timeStamp
            )
        }
    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentCoinPriceList,
        timeStamp: Date
    ) {
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp
        )
        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }
}