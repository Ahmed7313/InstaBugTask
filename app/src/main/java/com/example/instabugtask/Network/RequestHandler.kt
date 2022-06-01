package com.example.instabugtask.Network

import android.util.Log
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

     fun getMethod(testUrl: String) {
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
    }

    private fun postMethod() {

        // Uncomment the one you want to test, and comment the others

        //rawJSON()

        // urlEncoded()

        // formData()

    }

     fun <K, V> postMethod(jsonMap: Map<K, V>,requestPropertyMap: Map<K, V>, testUrl: String) : String{

        var prettyJson  = ""
        var responseHeader = ""
        var responseData = ""

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        for ((key, value) in jsonMap.entries) {
            jsonObject.put(key.toString(), value.toString())
        }

        // Convert JSONObject to String
            val jsonObjectString = jsonObject.toString()
            val url =URL(testUrl)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
        for ((key, value) in requestPropertyMap.entries) {
            httpURLConnection.setRequestProperty(key.toString(), value.toString())
        }
        // The format of the content we're sending to the server
            // httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
            //httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
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

                Log.d("Pretty Printed JSON :", prettyJson)
            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        return responseHeader
    }
}