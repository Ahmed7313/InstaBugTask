package com.example.instabugtask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class DetailsActivity : AppCompatActivity() {

    private lateinit var code : TextView
    private lateinit var body : TextView
    private lateinit var header : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        code = findViewById(R.id.code)
        body = findViewById(R.id.body)
        header = findViewById(R.id.header)

        val resultsCode = intent.getStringExtra("code")
        val resultsBody = intent.getStringExtra("body")
        val resultsHeader = intent.getStringExtra("header")

        code.text = resultsCode
        body.text = resultsBody
        header.text = resultsHeader


    }
}