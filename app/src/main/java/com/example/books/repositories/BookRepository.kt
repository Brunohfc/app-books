package com.example.books.repositories

import com.example.books.entities.BookEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class BookRepository private constructor(){

    private val books = mutableListOf<BookEntity>()

    init {
        books.addAll(getMockBooks())
    }

    companion object{
        private lateinit var instance : BookRepository

        @OptIn(InternalCoroutinesApi::class)
         fun getInstance(): BookRepository {
            synchronized(this){
                if(!::instance.isInitialized){
                    instance = BookRepository()
                }

            }
            return instance
        }
    }


    private fun getMockBooks(): List<BookEntity> {
        return listOf(
            BookEntity(1, "To Kill a Mockingbird", "Harper Lee", true, "Ficção"),
            BookEntity(2, "Dom Casmurro", "Machado de Assis", false, "Romance"),
            BookEntity(3, "O Hobbit", "J.R.R. Tolkien", true, "Fantasia"),
            BookEntity(4, "Cem Anos de Solidão", "Gabriel García Márquez", false, "Romance"),
            BookEntity(5, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", false, "Fantasia"),
            BookEntity(6, "Crime e Castigo", "Fiódor Dostoiévski", false, "Ficção policial"),
            BookEntity(7, "Frankenstein", "Mary Shelley", false, "Terror"),
            BookEntity(8, "Harry Potter e a Pedra Filosofal", "J.K. Rowling", false, "Fantasia"),
            BookEntity(9, "Neuromancer", "William Gibson", false, "Cyberpunk"),
            BookEntity(10, "Senhor dos Anéis", "J.R.R. Tolkien", false, "Fantasia"),
            BookEntity(11, "Drácula", "Bram Stoker", false, "Terror"),
            BookEntity(12, "Orgulho e Preconceito", "Jane Austen", false, "Romance"),
            BookEntity(13, "Harry Potter e a Câmara Secreta", "J.K. Rowling", false, "Fantasia"),
            BookEntity(14, "As Crônicas de Nárnia", "C.S. Lewis", false, "Fantasia"),
            BookEntity(15, "O Código Da Vinci", "Dan Brown", false, "Mistério"),
            BookEntity(16, "It: A Coisa", "Stephen King", false, "Terror"),
            BookEntity(17, "Moby Dick", "Herman Melville", true, "Aventura"),
            BookEntity(18, "O Nome do Vento", "Patrick Rothfuss", true, "Fantasia"),
            BookEntity(19, "O Conde de Monte Cristo", "Alexandre Dumas", true, "Aventura"),
            BookEntity(20, "Os Miseráveis", "Victor Hugo", false, "Romance")
        )
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


