package com.example.jiro.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConnectionHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "userDatabase.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "password TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        val result = db.insert("users", null, values)
        db.close()
        return (result != -1L)
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))
        val userExists = cursor.count > 0
        cursor.close()
        db.close()
        return userExists
    }
}
