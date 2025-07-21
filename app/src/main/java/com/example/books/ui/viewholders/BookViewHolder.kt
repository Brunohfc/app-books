package com.example.books.ui.viewholders


import androidx.recyclerview.widget.RecyclerView
import com.example.books.R
import com.example.books.databinding.ItemsBookBinding
import com.example.books.entities.BookEntity
import com.example.books.ui.listeners.BookListener


// vinculando o holder a view que desejo usar
class BookViewHolder(private val item: ItemsBookBinding, private val listener : BookListener) : RecyclerView.ViewHolder(item.root) {

    fun bind(book: BookEntity) {
        item.textViewBookTitle.text = book.title
        item.textViewAuthor.text = book.author
        item.textViewGenre.text = book.genre

        item.textViewBookTitle.setOnClickListener{
            listener.onClick(book.id)
        }

        setBackgroundColorOnGenre(item.textViewGenre.text.toString())
        setFavoriteIcon(book.favorite)
    }

    private fun setFavoriteIcon(isFavorite: Boolean){
        if(isFavorite){
            item.imageViewFavorite.setImageResource(R.drawable.ic_favorite)
        }else{
            item.imageViewFavorite.setImageResource(R.drawable.ic_favorite_empty)
        }
    }


    private fun setBackgroundColorOnGenre(genre: String){
        val TERROR = "Terror"
        val ROMANCE = "Romance"
        val FANTASIA = "Fantasia"
        when(genre){
            TERROR -> item.textViewGenre.setBackgroundResource(R.drawable.rounded_label_teal)
            ROMANCE -> item.textViewGenre.setBackgroundResource(R.drawable.rounded_label_fantasy)
            FANTASIA -> item.textViewGenre.setBackgroundResource(R.drawable.rounded_label_red)

        }
    }
}