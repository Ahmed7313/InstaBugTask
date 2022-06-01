package com.example.instabugtask

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.instabugtask.Network.RequestHandler.getMethod
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.w3c.dom.Text
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {


    private lateinit var getButton: Button
    private lateinit var postButton: Button
    private lateinit var noInternetText : TextView
    private lateinit var titleText : TextView
    private lateinit var noInternetImage : ImageView
    private lateinit var mainTitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getButton = findViewById(R.id.getBtn)
        postButton = findViewById(R.id.postBtn)
        noInternetText = findViewById(R.id.no_internet)
        titleText = findViewById(R.id.textView2)
        noInternetImage = findViewById(R.id.no_internet_image)
        mainTitle = findViewById(R.id.mainTitle)

        if (!checkForInternet(this)){
            getButton.isEnabled = false
            getButton.isClickable = false
            getButton.visibility = View.INVISIBLE

            postButton.isEnabled = false
            postButton.isClickable = false
            postButton.visibility = View.INVISIBLE

            noInternetImage.visibility = View.VISIBLE
            noInternetText.visibility = View.VISIBLE
            mainTitle.visibility = View.INVISIBLE
            titleText.visibility = View.INVISIBLE
        }

        getButton.setOnClickListener {
            val intent = Intent(this, GetActivity::class.java)
            startActivity(intent)
        }
        postButton.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
