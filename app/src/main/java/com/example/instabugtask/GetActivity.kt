package com.example.instabugtask

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.*
import com.example.instabugtask.Network.RequestHandler
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.w3c.dom.Text
import java.net.CacheResponse
import java.net.HttpURLConnection
import java.net.URL

class GetActivity : AppCompatActivity() {

    private lateinit var testedURL : EditText
    private lateinit var testButton: Button
    private  var string: String = ""
//    private lateinit var getCode: TextView
//    private lateinit var getBody: TextView
//    private lateinit var getHeader : TextView

    private lateinit var progressBar: ProgressBar

    var url : String = ""

     var resaultResponseCode : String = ""
     var resaultResponseBody : String = ""
     var resaultResponseHeader : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)

        testedURL = findViewById(R.id.url)
        testButton = findViewById(R.id.test_btn)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

//        getBody = findViewById(R.id.getBody)
//        getCode = findViewById(R.id.getCode)
//        getHeader = findViewById(R.id.getHeader)

        testButton.setOnClickListener {
            if (testedURL.text.isNotEmpty()){
                if ( URLUtil.isValidUrl(testedURL.text.toString())){
                    progressBar.visibility = View.VISIBLE
                    url = testedURL.text.toString()
                    Thread({
                        getMethod(url)
                    }).start()
                }else{
                    Toast.makeText(this, " Pleae enter a Valid URL", Toast.LENGTH_SHORT).show()

                }

            }else{
                Toast.makeText(this, " Pleae enter The URL first to be able to test it", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun getMethod(testUrl: String) {
        var prettyJson  = ""
        var responseHeader = ""
        var responseData = ""
        var header = ""

        val url = URL(testUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.doInput = true
        httpURLConnection.doOutput = false
        header = httpURLConnection.headerFields.entries.toString()

        // Check if the connection is successful
        val responseCode = httpURLConnection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = httpURLConnection.inputStream.bufferedReader()
                .use {
                    it.readText()
                }
            // defaults to UTF-8
            // Convert raw JSON to pretty JSON using GSON library
            val gson = GsonBuilder().setPrettyPrinting().create()
            prettyJson = gson.toJson(JsonParser.parseString(response))
            responseHeader = StringBuilder()
                .append(responseCode.toString())
                .append('\n')
                .append(response)
                .append('\n')
                .append(header).toString()

            resaultResponseCode = responseCode.toString()
            resaultResponseBody = prettyJson
            resaultResponseHeader = header
            progressBar.visibility = View.INVISIBLE

            val intent = Intent(this@GetActivity, DetailsActivity::class.java)
            intent.putExtra("body", resaultResponseBody)
            intent.putExtra("code", resaultResponseCode)
            intent.putExtra("header", resaultResponseHeader)
            this@GetActivity.startActivity(intent)

            Log.d("Pretty Printed JSON :", prettyJson)
        } else {
            Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            resaultResponseCode = responseCode.toString()
            resaultResponseBody = prettyJson
            resaultResponseHeader = header
            progressBar.visibility = View.INVISIBLE

            val intent = Intent(this@GetActivity, DetailsActivity::class.java)
            intent.putExtra("body", resaultResponseBody)
            intent.putExtra("code", resaultResponseCode)
            intent.putExtra("header", resaultResponseHeader)
            this@GetActivity.startActivity(intent)
        }
    }

}