package com.example.books.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.books.entities.BookEntity
import com.example.books.repositories.BookRepository

class DetailViewModel : ViewModel() {

    private val repository : BookRepository = BookRepository()

//    usando um objeto observavel ao buscar o livro
    private val _book = MutableLiveData<BookEntity>()
    val book: LiveData<BookEntity> = _book

    fun getBookById(id: Int){
        _book.value = repository.getOneBook(id)
    }
}