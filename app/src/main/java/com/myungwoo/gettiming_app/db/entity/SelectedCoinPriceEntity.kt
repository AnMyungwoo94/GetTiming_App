package com.myungwoo.gettiming_app.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "selected_coin_price_table")
data class SelectedCoinPriceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val coinName: String,
    val transaction_date: String,
    val type: String,
    val units_traded: String,
    val price: String,
    val total: String,
    val timeStamp: Date //Data는 DB에 저렇게 저장을 못해서 컨버터를 만들어줘야함

)

class DataConverters {
    //DB에 저장하고 뺄때 타입을 맞춰주는 것.
    @TypeConverter
    fun fromTimeStamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dataToTimeStamp(date: Date): Long {
        return date.time
    }
}