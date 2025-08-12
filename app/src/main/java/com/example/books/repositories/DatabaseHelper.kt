package com.example.books.repositories

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.books.entities.BookEntity
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
        insertBooks(db)
    }

    override fun onUpgrade(p0: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private fun insertBooks(db: SQLiteDatabase){
        val books = getMockBooks()
        for(book in books){
            val values = ContentValues().apply {
                put(DatabaseConstants.BOOKS.COLUMS.ID , book.id)
                put(DatabaseConstants.BOOKS.COLUMS.TITLE , book.title)
                put(DatabaseConstants.BOOKS.COLUMS.GENRE , book.genre)
                //convertendo boolean para inteiro por causa dos tipos do SQLite
                put(DatabaseConstants.BOOKS.COLUMS.FAVORITE , if(book.favorite) 1 else 0 )
            }
            db.insert(DatabaseConstants.BOOKS.TABLE_NAME, null, values )
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
}