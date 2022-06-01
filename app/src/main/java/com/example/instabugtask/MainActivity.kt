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


    private lateinit var getButton: Button
    private lateinit var postButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getButton = findViewById(R.id.getBtn)
        postButton = findViewById(R.id.postBtn)

        getButton.setOnClickListener {
            val intent = Intent(this, GetActivity::class.java)
            startActivity(intent)
        }
        postButton.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

}