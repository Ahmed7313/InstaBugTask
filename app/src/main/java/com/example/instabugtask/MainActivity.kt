package com.example.instabugtask

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instabugtask.Network.RequestHandler.getMethod
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var testedURL : EditText
    private lateinit var response : TextView
    private lateinit var testButton: Button
    private lateinit var postButton: Button
    private  var string: String = ""

    var url : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testedURL = findViewById(R.id.url)
        response = findViewById(R.id.response)
        testButton = findViewById(R.id.test_btn)
        postButton = findViewById(R.id.postviewbtn)

        testButton.setOnClickListener {
            url = testedURL.text.toString()
            Thread({
               string = getMethod(url)
            }).start()

            response.text = string
        }

        postButton.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }

}