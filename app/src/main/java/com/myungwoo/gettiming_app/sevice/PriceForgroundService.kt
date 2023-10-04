package com.myungwoo.gettiming_app.sevice

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.myungwoo.gettiming_app.R
import com.myungwoo.gettiming_app.dataModel.CurrentPrice
import com.myungwoo.gettiming_app.dataModel.CurrentPriceResult
import com.myungwoo.gettiming_app.repository.NetWorkRepository
import com.myungwoo.gettiming_app.view.activity.MainActivity
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PriceForgroundService : Service() {

    private val Notification_ID = 1000
    private val netWorkRepository = NetWorkRepository()
    lateinit var job: Job

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            "START" -> {

                job = CoroutineScope(Dispatchers.Default).launch {
                    while (true){
                        startForeground(Notification_ID, makeNotification())

                        delay(3000)
                    }
                }
            }
            "STOP" -> {
                Timber.d("STOP")

                try {
                    job.cancel()
                    stopForeground(true)
                    stopSelf()
                } catch (e : java.lang.Exception) {

                }

            }
        }

        return START_STICKY

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    suspend fun makeNotification(): Notification {
        val result = getAllCoinList()
        val randomNum = Random().nextInt(result.size)
        val title = result[randomNum].coinName
        val content = result[randomNum].coinInfo.fluctate_24H

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
            .setContentTitle("코인 이름 : $title")
            .setContentText("변동 가격 : $content")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "name"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
        return builder.build()
    }

    suspend fun getAllCoinList(): ArrayList<CurrentPriceResult> {
        val result = netWorkRepository.getCurrentCoinList()

        val currentPriceResultList = ArrayList<CurrentPriceResult>()

        //데이터 내가 원하는대로 가공하기
        for (coin in result.data) {
            try {
                val gson = Gson()
                val gsonTogson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonTogson, CurrentPrice::class.java)

                val curremtPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                currentPriceResultList.add(curremtPriceResult)
            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }
        }

        return currentPriceResultList
    }
}