package com.myungwoo.gettiming_app.db.dao

import androidx.room.*
import com.myungwoo.gettiming_app.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestCoinDAO {
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData(): Flow<List<InterestCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected: Boolean = true): List<InterestCoinEntity>
}