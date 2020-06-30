package com.example.businesscard.model

import androidx.lifecycle.LiveData

class BusinessRepo(  private val businessDao:BusinessDao)  {
  val allBusinessCards: List<BusinessCard> = businessDao.getAll()

    fun insert(businessCard: BusinessCard){
        businessDao.insertAll(businessCard)
    }

}