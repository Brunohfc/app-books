package com.example.books.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import com.example.books.entities.BookEntity
import com.example.books.repositories.BookRepository
// substituindo o viewModel pelo AndroidViewModel para ter o contexto da aplicacao
class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>> = _books

    private val repository = BookRepository.getInstance(application.applicationContext)

    fun favorite(id: Int){
        repository.toggleFavoriteBook(id)
    }

    fun getFavoriteBook(){
       _books.value = repository.getFavoriteBook()
    }
}