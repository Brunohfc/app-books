package com.example.books.helpers

class DatabaseConstants private constructor(){

     object BOOKS{
        const val TABLE_NAME = "Books"

         object COLUMS{
             const val ID = "Id"
             const val AUTHOR = "Author"
             const val TITLE = "Title"
             const val FAVORITE = "Favorite"
             const val GENRE = "Genre"

         }
    }
}