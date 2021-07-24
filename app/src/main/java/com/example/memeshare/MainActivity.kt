package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target




class MainActivity : AppCompatActivity() {

    private var cureentImageUrl:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        loadMeme()
    }

   private fun loadMeme(){
       val progressBar=findViewById<ProgressBar>(R.id.progress_bar)
       progressBar.visibility=View.VISIBLE

       // Instantiate the RequestQueue.

       val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
       val stringRequest = JsonObjectRequest(
           Request.Method.GET, url,null,
           { response ->
           cureentImageUrl=response.getString("url")
               val image: ImageView =findViewById(R.id.meme_image)

           Glide.with(this).load(cureentImageUrl).listener(object : RequestListener<Drawable> {

               override fun onLoadFailed(
                   e: GlideException?,
                   model: Any?,
                   target: Target<Drawable>?,
                   isFirstResource: Boolean
               ): Boolean {
                   progressBar.visibility=View.GONE
                   return false
               }

               override fun onResourceReady(
                   resource: Drawable?,
                   model: Any?,
                   target: Target<Drawable>?,
                   dataSource: DataSource?,
                   isFirstResource: Boolean
               ): Boolean {
                   progressBar.visibility=View.GONE
                   return false
               }

           }).into(image)


           },
           {
               Log.i("meme error",it.toString());

           Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
           })

// Add the request to the RequestQueue.
      MySingleton.getInstance(this).addToRequestQueue(stringRequest)

    }

    fun shareMeme(view: View) {
        val intent :Intent=Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"see this its is awesome!!!$cureentImageUrl")

        intent.type="text/plain"
        val chooser = Intent.createChooser(intent,"share this meme using")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
    loadMeme()
    }
}