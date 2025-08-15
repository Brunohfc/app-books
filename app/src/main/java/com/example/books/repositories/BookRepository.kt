package com.example.books.repositories

import android.content.Context
import com.example.books.entities.BookEntity
import com.example.books.helpers.DatabaseConstants
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class BookRepository private constructor(context: Context){

    private val books = mutableListOf<BookEntity>()
    private var database = DatabaseHelper(context)


    companion object{
        private lateinit var instance : BookRepository

        @OptIn(InternalCoroutinesApi::class)
         fun getInstance(context: Context): BookRepository {
            synchronized(this){
                if(!::instance.isInitialized){
                    instance = BookRepository(context)
                }

            }
            return instance
        }
    }

    fun getAllBooks(): List<BookEntity> {

        val books = mutableListOf<BookEntity>()
        val db = database.readableDatabase

        val cursor = db.query(DatabaseConstants.BOOKS.TABLE_NAME, null,null,null,null,null,null)

        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.AUTHOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.GENRE))
                val favorite: Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.BOOKS.COLUMS.FAVORITE)) == 1

                books.add(BookEntity(id,title,author,favorite,genre))
            }while (cursor.moveToNext())
        }

        db.close()
        cursor.close()

        return books
    }

    fun getFavoriteBook(): List<BookEntity> {
        return books.filter { it.favorite }
    }

    fun getOneBook(id: Int): BookEntity? {
        if (books.isNotEmpty()) {

            return books.find { it.id == id }
        }
        return null
    }

    // retorna boolean para tratar na viewModel
    fun deleteById(id: Int): Boolean {
        return books.removeIf { it.id == id }
    }

    fun toggleFavoriteBook(id: Int){
        val book = books.find {  it.id == id }
        if(book != null){
            book.favorite = !book.favorite
        }
    }

}


