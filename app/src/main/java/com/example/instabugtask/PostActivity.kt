package com.example.instabugtask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instabugtask.Network.RequestHandler

class PostActivity : AppCompatActivity() {

    private lateinit var testedURL : EditText
    private lateinit var response : TextView
    private lateinit var testButton: Button
    private  var string: String = ""
    private lateinit var keyJson : EditText
    private lateinit var valueJson : EditText
    private lateinit var keyRequest: EditText
    private lateinit var valueRequest: EditText
    var url : String = ""
    private lateinit var  keyjson : String
    private lateinit var  valuejson : String
    private lateinit var  keyrequest : String
    private lateinit var  valuerequest : String

    private lateinit var jsonMap: Map<String, String>
    private lateinit var requestPropertyMap: Map<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        testedURL = findViewById(R.id.urlpost)
        response = findViewById(R.id.responsepost)
        testButton = findViewById(R.id.post_btn)
        keyJson = findViewById(R.id.keyJson)
        valueJson = findViewById(R.id.valueJson)
        keyRequest = findViewById(R.id.keyRequest)
        valueRequest = findViewById(R.id.valueRequest)

        testButton.setOnClickListener {
            url = testedURL.text.toString()
            keyjson = keyJson.text.toString().trim()
            valuejson = valueJson.text.toString().trim()
            keyrequest = keyRequest.text.toString().trim()
            valuerequest = valueRequest.text.toString().trim()

            jsonMap = buildMap<String, String>(){
                put(keyjson, valuejson)
            }
            requestPropertyMap = buildMap<String, String>(){
                put(keyrequest, valuerequest)
            }
            Thread({

                string = RequestHandler.postMethod(jsonMap,requestPropertyMap, url)
            }).start()

            response.text = string
        }
    }
}