package com.example.businesscard.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusinessDao {
    @Query("SELECT * FROM businessCards")
    fun getAll(): List<BusinessCard>

    @Query("SELECT * FROM  businessCards WHERE ( :businessCardIds)")
    fun  getIds(businessCardIds: IntArray): List<BusinessCard>

    @Query("SELECT * FROM businessCards WHERE content LIKE :first AND "
    +"name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String):BusinessCard

    @Insert
    fun insertAll(vararg businessCards: BusinessCard)



    @Update
    fun updateBusinessCard(vararg businessCards: BusinessCard)
}