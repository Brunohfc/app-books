package com.example.books.repositories

import android.content.Context
import com.example.books.entities.BookEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class BookRepository private constructor(context: Context){

    private val books = mutableListOf<BookEntity>()
    private var database = DatabaseHelper(context)

    init {
        //lendo os dados que foram inseridos
        database.readableDatabase
    }

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


