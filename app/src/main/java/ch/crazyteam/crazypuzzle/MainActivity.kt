package ch.crazyteam.crazypuzzle

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.util.*

class MainActivity : AppCompatActivity() {

    // Define variables for used Elements
    private lateinit var imgDisplay: ImageView
    private lateinit var btnUpload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize variables with Values found in the Layout(View Layer)
        imgDisplay = findViewById<ImageView>(R.id.imgview_display)
        btnUpload = findViewById<Button>(R.id.btn_upload)

        // Set all required Listeners to corresponding Elements
        /*
        1. Create an Intent, which describes what happens if the Event occurs. For Example Intent.ACTION_GET_CONTENT
         gets the Content of a File, see Doc here:
         https://developer.android.com/guide/topics/providers/document-provider#overview.
        2. If the Intent has a Result, for example an Image gets picked, it is passed to startActivityForResult with
         three Parameters: requestCode, resultCode, data.
         - requestCode is a unique identifier for the Activity, and is defined in the companion object of this class
         - resultCode is a Constant which describes, whether the Result was achieved or not
         - data contains the Data that was fetched, for example the URI of the Image picked
         */
        btnUpload.setOnClickListener(){
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, IMAGE_REQUESTED);
        }
    }

    // Define all Results for different Activities
    /*
    onActivityResult describes what should happen with the Result you got from a Intent. For example, what should
     happen with the Image URI we got from picking an Image.
    Each if block is catching a different requestCode.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUESTED && resultCode == Activity.RESULT_OK) {
            imgDisplay.setImageURI(data?.data)
        }
    }

    // Define all Constants which are used in this Activity
    companion object {
        const val IMAGE_REQUESTED: Int = 101
    }


}
