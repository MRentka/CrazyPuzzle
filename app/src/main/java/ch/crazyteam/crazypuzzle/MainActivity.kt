package ch.crazyteam.crazypuzzle

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Define variables for used Elements
    private lateinit var imgDisplay: ImageView
    private lateinit var btnUpload: ImageButton
    private lateinit var btnCreatePuzzle: ImageButton
    private lateinit var lblPreview: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize variables with Values found in the Layout(View Layer)
        btnUpload = btn_upload
        btnCreatePuzzle = btn_create_puzzle
        lblPreview = lbl_preview
        imgDisplay = imgview_display

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
        btnCreatePuzzle.setOnClickListener() {
            val intent = Intent(this, UploadImageActivity::class.java).apply{}
            if(imgDisplay.visibility == View.VISIBLE) startActivity(intent) else {
                Snackbar.make(main_constraint_layout, R.string.snackbar_image_not_uploaded, Snackbar.LENGTH_SHORT)
                    .show();
            }
        }
    }

    // Define all Results for different Activities
    /*
    onActivityResult describes what should happen with the Result you got from a Intent. For
    example, what should happen with the Image URI we got from picking an Image.
    Each if block is catching a different requestCode.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUESTED && resultCode == Activity.RESULT_OK) {
            imgDisplay.setImageURI(data?.data)
            imgDisplay.visibility = View.VISIBLE
            togglePreviewText()
        }
    }

    private fun togglePreviewText() {
        if(imgDisplay.visibility == View.VISIBLE) lblPreview.visibility = View.INVISIBLE
        else lblPreview.visibility = View.INVISIBLE
    }

    // Define all Constants which are used in this Activity
    companion object {
        const val IMAGE_REQUESTED: Int = 101
    }
}
