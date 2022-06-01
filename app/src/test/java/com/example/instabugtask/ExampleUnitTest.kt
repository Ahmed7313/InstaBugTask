package com.example.instabugtask

import com.example.instabugtask.Network.RequestHandler
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun getRequest_withQUery_Parameters(){
        val testUrl = "https://httpbin.org/get"

        val responseCode = "200"
        assertEquals(responseCode, RequestHandler.getMethod(testUrl));
    }


    @Test
    fun PostRequest_Test(){
        val testUrl = "https://httpbin.org/post"
        val responseCode = "200"
        assertEquals(responseCode, RequestHandler.postMethod(testUrl))
    }
}