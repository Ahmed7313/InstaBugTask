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
    private  var  keyjson : String = ""
    private  var  valuejson : String = ""
    private  var  keyrequest : String = "Accept"
    private  var  valuerequest : String = "application/json"
    private lateinit var addHeader: Button
    private lateinit var addBody: Button
    var resaultResponseCode : String = ""
    var resaultResponseBody : String = ""
    var resaultResponseHeader : String = ""

    //private lateinit var jsonMap: Map<String, String>
    private lateinit var jsonMap : MutableMap<String, String>
    private lateinit var requestPropertyMap: MutableMap<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)


        testPostButton = findViewById(R.id.test_btnpost)
        testePostdURL = findViewById(R.id.urlPost)
        addHeader = findViewById(R.id.add_headers)
        addBody = findViewById(R.id.add_body)
        progressBar = findViewById(R.id.progressBarpost)
        progressBar.visibility = View.INVISIBLE

        jsonMap = mutableMapOf(keyjson to valuejson)
        requestPropertyMap = mutableMapOf(keyrequest to valuerequest)

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
            Thread({
                postMethod(url)
            }).start()

        }
    }

    private fun postMethod(testUrl: String) {

        var prettyJson = ""
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
                 prettyJson = gson.toJson(JsonParser.parseString(response))
                Log.d("Pretty Printed JSON :", prettyJson)

                    // Open DetailsActivity with the results
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("code", responseCode.toString())
                intent.putExtra("body", prettyJson)
                intent.putExtra("header", header)
                    this.startActivity(intent)

                progressBar.visibility = View.INVISIBLE

            } else {
//                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
//                runOnUiThread {
//                Toast.makeText(this, " Response Code is: ${responseCode}", Toast.LENGTH_SHORT).show()
//                    progressBar.visibility = View.INVISIBLE
//                }
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
                resaultResponseCode = responseCode.toString()
                resaultResponseBody = prettyJson
                resaultResponseHeader = header
                progressBar.visibility = View.INVISIBLE

                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("body", resaultResponseBody)
                intent.putExtra("code", resaultResponseCode)
                intent.putExtra("header", resaultResponseHeader)
                this.startActivity(intent)
            }
        }

    private fun showDialogHeader() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialogue_view)
        val bodyKey = dialog.findViewById(R.id.key) as EditText
        val bodyvalue = dialog.findViewById(R.id.value) as EditText
        val addBtn = dialog.findViewById(R.id.dialogDismiss_button) as Button
        val finishBtn = dialog.findViewById(R.id.dialogDismiss_finish_button) as Button
        requestPropertyMap = mutableMapOf(keyrequest to valuerequest)

        addBtn.setOnClickListener {
            val keyrequest = bodyKey.text.toString().trim()
            val valuerequest = bodyvalue.text.toString().trim()
            requestPropertyMap.put(keyrequest, valuerequest)


            Toast.makeText(this, "${bodyKey.text} Added", Toast.LENGTH_SHORT).show()
            bodyKey.text.clear()
            bodyvalue.text.clear()
        }
        finishBtn.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, " number of key value ${requestPropertyMap.size}", Toast.LENGTH_SHORT).show()

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
        jsonMap = mutableMapOf(keyjson to valuejson)

        addBtn.setOnClickListener {
            val keyjson = bodyKey.text.toString().trim()
            val valuejson = bodyvalue.text.toString().trim()
            jsonMap.put(keyjson, valuejson)


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