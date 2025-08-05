package com.example.books.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.books.entities.BookEntity
import com.example.books.repositories.BookRepository

class FavoritesViewModel : ViewModel() {

    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>> = _books

    private val repository = BookRepository.getInstance()

    fun favorite(id: Int){
        repository.toggleFavoriteBook(id)
    }

    fun getFavoriteBook(){
       _books.value = repository.getFavoriteBook()
    }
}