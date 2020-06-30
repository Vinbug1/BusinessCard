package com.example.businesscard.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusinessViewModel(application: Application): AndroidViewModel(application) {
    private val repo: BusinessRepo

    private val allBusinessCards: List<BusinessCard>

    init {
        val busiDao = BusinessCardDatabase
            .getInstance(application)?.businessDao()
        repo = busiDao?.let { BusinessRepo(it) }!!
        allBusinessCards = repo.allBusinessCards
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(businessCard: BusinessCard) = viewModelScope.launch(Dispatchers.IO) {
        repo.insert(businessCard)
    }
}