package com.example.books.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.books.helpers.DatabaseConstants

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {



        companion object{
            private const val DB_NAME = "BooksDB"
            private const val DB_VERSION = 1

            private val CREATE_TABLE_BOOKS = """
                CREATE TABLE ${DatabaseConstants.BOOKS.TABLE_NAME}(
                    ${DatabaseConstants.BOOKS.COLUMS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                    ${DatabaseConstants.BOOKS.COLUMS.AUTHOR} TEXT NOT NULL,
                    ${DatabaseConstants.BOOKS.COLUMS.TITLE} TEXT NOT NULL,
                    ${DatabaseConstants.BOOKS.COLUMS.FAVORITE} INTEGER NOT NULL,
                    ${DatabaseConstants.BOOKS.COLUMS.GENRE} TEXT NOT NULL
                );
                
            """.trimIndent()
        }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_BOOKS)
    }

    override fun onUpgrade(p0: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}