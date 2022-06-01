package com.example.instabugtask.database

import android.provider.BaseColumns

object ResponsesContract {
    // Table contents are grouped together in an anonymous object.
    object ResponseEntry : BaseColumns {
        const val RESPONSE_CODE = "code"
        const val RESPONSE_BODY = "body"
        const val RESPONSE_HEADERS = "headers"
    }
}
