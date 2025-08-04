package com.example.books.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.books.databinding.ItemsBookBinding
import com.example.books.entities.BookEntity
import com.example.books.ui.listeners.BookListener
import com.example.books.ui.viewholders.BookViewHolder


class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

//    pegando os dados da entidade e manupilando no adpater
    private var books: List<BookEntity> = listOf()

    private lateinit var bookListener: BookListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = ItemsBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view, bookListener)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    fun updateBooks(list: List<BookEntity>){
        books = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: BookListener){
        bookListener = listener
    }
}