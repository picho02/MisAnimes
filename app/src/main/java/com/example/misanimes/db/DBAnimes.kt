package com.example.misanimes.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.misanimes.model.Anime

class DBAnimes (context: Context?) : DBHelper(context) {
    //Aqui va el codigo para el crud
    val context = context

    fun insertAnime(title: String, temporadas: Int, genero: Int): Long {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0
        try {
            val values = ContentValues()
            values.put("title", title)
            values.put("temporadas", temporadas)
            values.put("genero", genero)

            id = db.insert(TABLE_ANIMES, null, values)
        } catch (e: Exception) {

        } finally {
            db.close()
        }

        return id
    }

    fun getAnimes(): ArrayList<Anime> {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var listAnimes = ArrayList<Anime>()
        var animeTmp: Anime? = null
        var cursorAnimes: Cursor? = null

        cursorAnimes = db.rawQuery("SELECT * FROM $TABLE_ANIMES", null)

        if (cursorAnimes.moveToFirst()) {
            do {
                animeTmp = Anime(
                    cursorAnimes.getInt(0),
                    cursorAnimes.getString(1),
                    cursorAnimes.getInt(2),
                    cursorAnimes.getInt(3)
                )
                listAnimes.add(animeTmp)
            } while (cursorAnimes.moveToNext())
        }

        cursorAnimes.close()

        return listAnimes
    }

    fun getAnime(id: Int): Anime? {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var anime: Anime? = null
        var cursorAnimes: Cursor? = null

        cursorAnimes = db.rawQuery("SELECT * FROM $TABLE_ANIMES WHERE id = $id LIMIT 1", null)
        if (cursorAnimes.moveToFirst()) {
            anime = Anime(
                cursorAnimes.getInt(0),
                cursorAnimes.getString(1),
                cursorAnimes.getInt(2),
                cursorAnimes.getInt(3)
            )
        }

        cursorAnimes.close()

        return anime

    }

    fun updateAnime(id: Int, title: String, temporadas: Int, genero: Int): Boolean {
        var banderaCorrecto = false
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        try {
            db.execSQL("UPDATE $TABLE_ANIMES SET title = '$title', temporadas = '$temporadas', genero = '$genero' WHERE id = $id")
            banderaCorrecto = true

        } catch (e: Exception) {

        } finally {
            db.close()
        }
        return banderaCorrecto
    }

    fun deleteAnime(id: Int): Boolean {

        var banderaCorrecto = true

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("DELETE FROM $TABLE_ANIMES WHERE id = $id")
            banderaCorrecto = true
        } catch (e: Exception) {

        } finally {
            db.close()
        }

        return banderaCorrecto
    }


}
