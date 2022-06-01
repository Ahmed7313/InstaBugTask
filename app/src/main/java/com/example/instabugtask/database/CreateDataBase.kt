package com.example.instabugtask.database

import android.provider.BaseColumns
import com.example.instabugtask.database.FeedReaderContract.FeedEntry

 const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${FeedEntry.RESPONSE_CODE} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedEntry.RESPONSE_BODY} TEXT," +
            "${FeedEntry.RESPONSE_HEADERS} TEXT)"

 const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.RESPONSE_CODE}"