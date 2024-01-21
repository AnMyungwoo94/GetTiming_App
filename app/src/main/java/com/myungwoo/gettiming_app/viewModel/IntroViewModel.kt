package com.myungwoo.gettiming_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myungwoo.gettiming_app.dataStore.MyDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class IntroViewModel : ViewModel() {

    private val _frist = MutableLiveData<Boolean>()
    val frist: LiveData<Boolean>
        get() = _frist

    fun checkFirstFlag() = viewModelScope.launch {
        delay(2000)
        val getData = MyDataStore().getFirstData()
        _frist.value = getData
        Timber.d(getData.toString())
    }
}