package com.example.books.ui.viewholders


import androidx.recyclerview.widget.RecyclerView
import com.example.books.databinding.ItemsBookBinding
import com.example.books.entities.BookEntity

// vinculando o holder a view que desejo usar
class BookViewHolder(private val item : ItemsBookBinding)
    : RecyclerView.ViewHolder(item.root) {

        fun bind(book: BookEntity){
            item.textViewBookTitle.text = book.title
            item.textViewAuthor.text = book.author
            item.textViewGenre.text = book.genre
        }
}