package com.example.businesscard

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.businesscard.model.BusinessCard
import com.example.businesscard.model.BusinessCardDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_card.*
import kotlinx.android.synthetic.main.new_card.view.*
import kotlinx.android.synthetic.main.new_card.view.image
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {

    private lateinit var db: BusinessCardDatabase
     var businesscards: List<BusinessCard>? =null
     var mCounter =  -1
     var sContent:TextView? = null
    var sName:TextView? = null
    var sImage:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val btn = findViewById<Button>(R.id.button)
          sContent = findViewById(R.id.content)
         sName = findViewById(R.id.name)
         sImage = findViewById(R.id.image)

         btn.setOnClickListener {
             mCounter++
             val businesscards = businesscards?.get(mCounter)
             if (businesscards != null){
                 showData(businesscards)
             }

         }

        val mFab = findViewById<FloatingActionButton>(R.id.fab)
        mFab.setOnClickListener() {
            intent = Intent(this, NewCardActivity::class.java)
           startActivity(intent)
        }
          displayData( )
    }

    private  fun displayData() {
        if (BusinessCardDatabase.getInstance(this) != null){
            db = BusinessCardDatabase.getInstance(this)!!
        }

        RetrieveTask(this).execute()
    }

    private fun showData(businessCard: BusinessCard){
        sContent?.text = businessCard.content
        sName?.text = businessCard.name
        //
        sImage?.setImageURI(Uri.parse(businessCard.image))
    }

    private open class RetrieveTask// only retain a weak reference to the activity
    internal constructor(context:MainActivity): AsyncTask<Void, Void, List<BusinessCard>>() {
        private val activityReference: WeakReference<MainActivity> = WeakReference(context)
       override fun doInBackground(vararg voids:Void): List<BusinessCard>? {
            if (activityReference.get() != null)
                return activityReference.get()!!.db.businessDao().getAll()
            else
                return null
        }
         override fun onPostExecute(businesscards:List<BusinessCard>) {
             Log.e("TAG", "businesscards:$businesscards")
            if (businesscards.isNotEmpty())
            {
                activityReference.get()?.businesscards = businesscards
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}




