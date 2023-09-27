package com.myungwoo.gettiming_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.myungwoo.gettiming_app.db.entity.InterestCoinEntity
import com.myungwoo.gettiming_app.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    //DB에 저장된 값들 가져오기
    private val dbRepository = DBRepository()
    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>

    //CoinListFragment

    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {
        if(interestCoinEntity.selected){
            interestCoinEntity.selected = false
        }else{
            interestCoinEntity.selected = true
        }
        dbRepository.updateInterestCoinData(interestCoinEntity)
    }
}