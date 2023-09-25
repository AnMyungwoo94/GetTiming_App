package com.myungwoo.gettiming_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.myungwoo.gettiming_app.dataModel.CurrentPriceResult
import com.myungwoo.gettiming_app.dataModel.CurrentPrice
import com.myungwoo.gettiming_app.repository.NetWorkRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    //LiveData를 사용하여 데이터를 관찰
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    fun getCurrentCoinLisr() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

        //데이터 내가 원하는대로 가공하기
        for (coin in result.data) {
            try {
                val gson = Gson()
                val gsonTogson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonTogson, CurrentPrice::class.java)

                val curremtPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
//                Timber.d(curremtPriceResult.toString())
                currentPriceResultList.add(curremtPriceResult)
            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }
        }
        _currentPriceResult.value = currentPriceResultList
    }
}