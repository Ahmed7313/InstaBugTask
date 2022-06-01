package com.example.instabugtask.database

import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val RESPONSE_CODE = "entry"
        const val RESPONSE_BODY = "title"
        const val RESPONSE_HEADERS = "subtitle"
    }
}