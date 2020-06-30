package com.example.businesscard.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.Internal
import okhttp3.internal.Internal.instance
import kotlin.reflect.KParameter

@Database(entities = [BusinessCard::class], version = 1)
abstract class BusinessCardDatabase: RoomDatabase() {
    abstract fun businessDao(): BusinessDao


    companion object{

            fun getInstance(context: Context): BusinessCardDatabase? {

                return  buildDatabaseInstance(context)
            }
                private fun buildDatabaseInstance(context: Context):BusinessCardDatabase{
                    return Room.databaseBuilder(
                            context.applicationContext,
                            BusinessCardDatabase::class.java, "Business-Card.db"
                    ).allowMainThreadQueries().build()

                }

            }


}



//        private class  BusinessCardDatabaseCallback(
//            private val scope: CoroutineScope
//        ) : RoomDatabase.Callback(){
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var businessDao = database.businessDao()
//
//                    businessDao.deleteAll()
//
//                    var businessCard =
//                        BusinessCard("If you don't" +
//                                " learn how to leave your friends,your" +
//                                " friends will leave ","VincentWilliams"
//                        )
//                    businessDao.insertAll(businessCard)
//                }
//            }
//        }














