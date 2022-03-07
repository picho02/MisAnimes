package com.example.misanimes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(
    context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $TABLE_ANIMES (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL,temporadas INTEGER NOT NULL,genero INTEGER NOT NULL)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE $TABLE_ANIMES")
        onCreate(p0)
    }
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "animes.db"
        public const val TABLE_ANIMES = "animes"
    }
}