package com.example.businesscard

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.businesscard.model.BusinessCard
import com.example.businesscard.model.BusinessCardDatabase
import kotlinx.android.synthetic.main.new_card.*
import kotlinx.android.synthetic.main.new_card.view.*
import java.lang.ref.WeakReference


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
open class NewCardActivity: AppCompatActivity(){

    //image pick code
    val IMAGE_PICK_CODE = 1000;

    //Permission code
     val PERMISSION_CODE = 1001;

      //Initialise Database
       var businessCardDatabase: BusinessCardDatabase? = null
      var businessCard: BusinessCard? = null

    lateinit var mContent:EditText
    lateinit var mName:EditText
    var imagePath: Uri? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_card)
        var saveBtn = findViewById<Button>(R.id.save_button)
         mContent = findViewById(R.id.content)
          var mImage = findViewById<ImageView>(R.id.image)
         mName = findViewById(R.id.name)

        businessCardDatabase  = BusinessCardDatabase.getInstance(this@NewCardActivity)

        saveBtn.setOnClickListener {
            businessCard = BusinessCard( 0,
                content.text.toString(),
                name.text.toString(),
                imagePath.toString()
            )
            // create worker thread to insert data into database
            InsertTask(this@NewCardActivity, businessCard!!).execute()
        }

        mImage.setOnClickListener {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               if (checkSelfPermission(READ_EXTERNAL_STORAGE) ==
                   PackageManager.PERMISSION_DENIED){
                   //permission denied
                   val permissions = arrayOf(READ_EXTERNAL_STORAGE);
                   //show popup to request runtime permission
                   requestPermissions(permissions, PERMISSION_CODE);
               }
               else{
                   //permission already granted
                   pickImageFromGallery();
               }
           }
           else{
               //system OS is < Marshmallow
               pickImageFromGallery();
           }
       }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

        //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
             imagePath = data?.data
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            image.setImageURI(data?.data)
        }
    }


    private fun setResult(businessCard: BusinessCard, flag:Int) {
        //setResult(flag, Intent().putExtra("businessCard", businessCard))
        finish()
    }

    private open class InsertTask// only retain a weak reference to the activity

    internal constructor(context:NewCardActivity,  var businessCard: BusinessCard):
        AsyncTask<Void, Void, Boolean>() {

        private val activityReference: WeakReference<NewCardActivity> = WeakReference(context)

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs:Void):Boolean {
            activityReference.get()?.businessCardDatabase?.businessDao()?.insertAll(businessCard)
            return true
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(bool:Boolean) {
            if (bool)
            {
                activityReference.get()?.setResult(businessCard, 1)
            }
        }
    }
}











