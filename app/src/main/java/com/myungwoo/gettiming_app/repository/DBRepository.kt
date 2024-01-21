package com.myungwoo.gettiming_app.repository

import com.myungwoo.gettiming_app.App
import com.myungwoo.gettiming_app.db.CoinPriceDatabase
import com.myungwoo.gettiming_app.db.entity.InterestCoinEntity
import com.myungwoo.gettiming_app.db.entity.SelectedCoinPriceEntity

class DBRepository {

    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    fun getAllInterestCoinData() = db.interestCoinDAO().getAllData()

    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().insert(interestCoinEntity)

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().update(interestCoinEntity)

    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

    fun getAllCoinPriceData() = db.selectedCoinPriceDAO().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinPriceDAO().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName: String) = db.selectedCoinPriceDAO().getOneCoinData(coinName)
}