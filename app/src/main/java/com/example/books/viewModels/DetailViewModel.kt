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

    private val _bookRemove = MutableLiveData<Boolean>()
    val bookRemove: LiveData<Boolean> = _bookRemove

    fun getBookById(id: Int){
        _book.value = repository.getOneBook(id)
    }

    fun removeBookById(id: Int){
        _bookRemove.value = repository.deleteById(id)
    }

    fun favoriteBookById(id: Int){
        repository.toggleFavoriteBook(id)
    }
}