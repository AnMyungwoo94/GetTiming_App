package com.myungwoo.gettiming_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.myungwoo.gettiming_app.dataModel.CurrentPriceResult
import com.myungwoo.gettiming_app.dataModel.CurrentPrice
import com.myungwoo.gettiming_app.dataStore.MyDataStore
import com.myungwoo.gettiming_app.db.entity.InterestCoinEntity
import com.myungwoo.gettiming_app.repository.DBRepository
import com.myungwoo.gettiming_app.repository.NetWorkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private val _save = MutableLiveData<String>()
    val save: LiveData<String>
        get() = _save

    fun getCurrentCoinList() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

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
        _currentPriceResult.value = currentPriceResultList
    }

    fun setupFristFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =
        viewModelScope.launch(Dispatchers.IO) {
            for (coin in currentPriceResultList) {
                val selected = selectedCoinList.contains(coin.coinName)
                val interestCoinEntity = InterestCoinEntity(
                    0,
                    coin.coinName,
                    coin.coinInfo.opening_price,
                    coin.coinInfo.closing_price,
                    coin.coinInfo.min_price,
                    coin.coinInfo.max_price,
                    coin.coinInfo.units_traded,
                    coin.coinInfo.acc_trade_value,
                    coin.coinInfo.prev_closing_price,
                    coin.coinInfo.units_traded_24H,
                    coin.coinInfo.acc_trade_value_24H,
                    coin.coinInfo.fluctate_24H,
                    coin.coinInfo.fluctate_rate_24H,
                    selected
                )
                interestCoinEntity.let {
                    dbRepository.insertInterestCoinData(it)
                }
            }
            withContext(Dispatchers.Main) {
                _save.value = "done"
            }
        }
}