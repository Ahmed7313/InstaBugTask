package com.example.instabugtask.Network

import android.content.Intent
import android.util.Log
import com.example.instabugtask.DetailsActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection


object RequestHandler {

    lateinit var resaultResponseCode : String
    lateinit var resaultResponseBody : String
    lateinit var resaultResponseHeader : String

     fun getMethod(testUrl: String) : String {
        var prettyJson  = ""
        var responseHeader = ""
        var responseData = ""

        val url =URL(testUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.doInput = true
        httpURLConnection.doOutput = false


        // Check if the connection is successful
        val responseCode = httpURLConnection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val response = httpURLConnection.inputStream.bufferedReader()
                .use {
                    it.readText()
                }
            // defaults to UTF-8
            var header = httpURLConnection.headerFields.entries.toString()

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


            Log.d("Pretty Printed JSON :", prettyJson)
        } else {
            Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
        }
         return responseCode.toString()
    }


     fun postMethod(testUrl : String) : String{

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("name", "Jack")
        jsonObject.put("salary", "3540")
        jsonObject.put("age", "23")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

           val url =URL(testUrl)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
            httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            // Send the JSON we created
            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
            outputStreamWriter.write(jsonObjectString)
            outputStreamWriter.flush()

            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    Log.d("Pretty Printed JSON :", prettyJson)


            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        return responseCode.toString()
    }
}