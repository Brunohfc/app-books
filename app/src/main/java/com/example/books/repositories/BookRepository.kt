package com.example.books.repositories

import android.content.Context
import com.example.books.entities.BookEntity
import com.example.books.helpers.DatabaseConstants
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class BookRepository private constructor(context: Context) {

    private val books = mutableListOf<BookEntity>()
    private var database = DatabaseHelper(context)


    companion object {
        private lateinit var instance: BookRepository

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): BookRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = BookRepository(context)
                }

            }
            return instance
        }
    }

    fun getAllBooks(): List<BookEntity> {

        val books = mutableListOf<BookEntity>()
        val db = database.readableDatabase

        val cursor =
            db.query(DatabaseConstants.BOOKS.TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.TITLE))
                val author =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.AUTHOR))
                val genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.GENRE))
                val favorite: Boolean =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books
    }

    fun getFavoriteBook(): List<BookEntity> {

        val books = mutableListOf<BookEntity>()
        val db = database.readableDatabase

        //filtrando os dados na coluna favorito que contem 1
        val cursor = db.query(
            DatabaseConstants.BOOKS.TABLE_NAME, null,
            "${DatabaseConstants.BOOKS.COLUMS.FAVORITE} = ?", arrayOf("1"), null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.TITLE))
                val author =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.AUTHOR))
                val genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.GENRE))
                val favorite: Boolean =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books
    }

    fun getOneBookById(id: Int): BookEntity? {

        var book: BookEntity? = null
        val db = database.readableDatabase

        val cursor = db.query(DatabaseConstants.BOOKS.TABLE_NAME, null,
            "${ DatabaseConstants.BOOKS.COLUMS.ID } = ?", arrayOf(id.toString()),null,null,null)

        if(cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.TITLE))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.AUTHOR))
            val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.GENRE))
            val favorite: Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.FAVORITE)) == 1

            book = BookEntity(id,title,author,favorite,genre)
        }

        cursor.close()
        db.close()

        return book
    }

    // retorna boolean para tratar na viewModel
    fun deleteById(id: Int): Boolean {
        return books.removeIf { it.id == id }
    }

    fun toggleFavoriteBook(id: Int) {
        val book = books.find { it.id == id }
        if (book != null) {
            book.favorite = !book.favorite
        }
    }

}


