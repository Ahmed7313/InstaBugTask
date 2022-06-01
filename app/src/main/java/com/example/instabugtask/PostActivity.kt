package com.example.instabugtask

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class PostActivity : AppCompatActivity() {

    private lateinit var testePostdURL : EditText
    private lateinit var testPostButton: Button
    private lateinit var progressBar: ProgressBar
    var url : String = ""
    private lateinit var  keyjson : String
    private lateinit var  valuejson : String
    private lateinit var  keyrequest : String
    private lateinit var  valuerequest : String
    private lateinit var addHeader: Button
    private lateinit var addBody: Button
    var resaultResponseCode : String = ""
    var resaultResponseBody : String = ""
    var resaultResponseHeader : String = ""

    //private lateinit var jsonMap: Map<String, String>
    var jsonMap : HashMap<String, String> = HashMap<String, String> ()
    private lateinit var requestPropertyMap: Map<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)


        testPostButton = findViewById(R.id.test_btnpost)
        testePostdURL = findViewById(R.id.urlPost)
        addHeader = findViewById(R.id.add_headers)
        addBody = findViewById(R.id.add_body)
        progressBar = findViewById(R.id.progressBarpost)
        progressBar.visibility = View.INVISIBLE

//        jsonMap = buildMap<String, String>(){
//            put("", "")
//        }

        addHeader.setOnClickListener {
            showDialogHeader()
        }
        addBody.setOnClickListener {
            showDialogBody()
        }

        testPostButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            url = testePostdURL.text.toString()
            requestPropertyMap = buildMap{
                put("Content-Type", "application/json")
            }
            Thread({
                postMethod(url)
            }).start()

        }
    }

    private fun postMethod(testUrl: String) {

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonMap.forEach { (key, value) -> jsonObject.put(key, value) }

//        jsonObject.put("name", "Jack")
//        jsonObject.put("salary", "3540")
//        jsonObject.put("age", "23")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()


            val url = URL(testUrl)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            requestPropertyMap.forEach { (key, value) ->  httpURLConnection.setRequestProperty(key, value)  }
            //httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
           // httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            // Send the JSON we created
            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
            outputStreamWriter.write(jsonObjectString)
            outputStreamWriter.flush()
            var header = httpURLConnection.headerFields.entries.toString()
            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8

                    // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(JsonParser.parseString(response))
                Log.d("Pretty Printed JSON :", prettyJson)

                    // Open DetailsActivity with the results
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("code", responseCode.toString())
                intent.putExtra("body", prettyJson)
                intent.putExtra("header", header)
                    this.startActivity(intent)

            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }


//    fun postMethod(testUrl: String){
//
//        var prettyJson  = ""
//        var responseHeader = ""
//        var responseData = ""
//
//        // Create JSON using JSONObject
//        val jsonObject = JSONObject()
//        if (jsonMap.isEmpty()){
//            for ((key, value) in jsonMap) {
//                jsonObject.put(key, value)
//                Log.d("map test :", jsonMap.keys.toString() + jsonMap.values.toString())
//            }
//        }
//
//        // Convert JSONObject to String
//        val jsonObjectString = jsonObject.toString()
//        val url = URL(testUrl)
//        val httpURLConnection = url.openConnection() as HttpURLConnection
//        httpURLConnection.requestMethod = "POST"
//        for ((key, value) in requestPropertyMap) {
//            httpURLConnection.setRequestProperty(key, value)
//        }
//        // The format of the content we're sending to the server
//        // httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
//        //httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
//        httpURLConnection.doInput = true
//        httpURLConnection.doOutput = true
//
//        // Send the JSON we created
//        val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
//        outputStreamWriter.write(jsonObjectString)
//        outputStreamWriter.flush()
//        var header = httpURLConnection.headerFields.entries.toString()
//
//        // Check if the connection is successful
//        val responseCode = httpURLConnection.responseCode
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            val response = httpURLConnection.inputStream.bufferedReader()
//                .use { it.readText() }  // defaults to UTF-8
//
//
//
//            // Convert raw JSON to pretty JSON using GSON library
//            val gson = GsonBuilder().setPrettyPrinting().create()
//            prettyJson = gson.toJson(JsonParser.parseString(response))
//            responseHeader = StringBuilder()
//                .append(responseCode.toString())
//                .append('\n')
//                .append(response)
//                .append('\n')
//                .append(header).toString()
//            val intent = Intent(this, DetailsActivity::class.java)
//            intent.putExtra("body", resaultResponseBody)
//            intent.putExtra("code", resaultResponseCode)
//            intent.putExtra("header", resaultResponseHeader)
//            this.startActivity(intent)
//            progressBar.visibility = View.INVISIBLE
//
//            Log.d("Pretty Printed JSON :", prettyJson + responseCode.toString() + resaultResponseHeader)
//        } else {
//            Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
//            resaultResponseCode = responseCode.toString()
//            resaultResponseBody = prettyJson
//            resaultResponseHeader = header
//
//            val intent = Intent(this, DetailsActivity::class.java)
//            intent.putExtra("body", resaultResponseBody)
//            intent.putExtra("code", resaultResponseCode)
//            intent.putExtra("header", resaultResponseHeader)
//            this.startActivity(intent)
//            progressBar.visibility = View.INVISIBLE
//
//        }
//    }

    private fun showDialogHeader() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialogue_view)
        val bodyKey = dialog.findViewById(R.id.key) as EditText
        val bodyvalue = dialog.findViewById(R.id.value) as EditText
        val startBtn = dialog.findViewById(R.id.dialogDismiss_button) as Button
        startBtn.setOnClickListener {
            keyjson = bodyKey.text.toString().trim()
            valuejson = bodyvalue.text.toString().trim()


            requestPropertyMap = buildMap{
                put(keyjson, valuejson)
            }
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showDialogBody() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialogue_view)
        val bodyKey = dialog.findViewById(R.id.key) as EditText
        val bodyvalue = dialog.findViewById(R.id.value) as EditText
        val addBtn = dialog.findViewById(R.id.dialogDismiss_button) as Button
        val finishBtn = dialog.findViewById(R.id.dialogDismiss_finish_button) as Button
        keyjson = bodyKey.text.toString().trim()
        valuejson = bodyvalue.text.toString().trim()
        addBtn.setOnClickListener {
            jsonMap.put(keyjson, valuejson)
            jsonMap.put("name", "Ahmed")
            jsonMap.put("Hight", "25")

            Toast.makeText(this, "${bodyKey.text} Added", Toast.LENGTH_SHORT).show()
            bodyKey.text.clear()
            bodyvalue.text.clear()
        }
        finishBtn.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, " number of key value ${jsonMap.size}", Toast.LENGTH_SHORT).show()

        }
        dialog.show()

    }
}