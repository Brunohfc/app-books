package com.example.books.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.books.R
import com.example.books.databinding.FragmentDetailBinding
import com.example.books.helpers.BookConstants
import com.example.books.viewModels.DetailViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private var bookId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container,false)

        arrowBack()
        setObservers()

//        recebendo o dado da home
        bookId = arguments?.getInt(BookConstants.KEY.BOOK_ID) ?: 0

//        usando a viewModel para pegar o dado do repository
        viewModel.getBookById(bookId)

        return binding.root
    }

//    acessa o viewModel e pega os dados que vem do repository e insere na view
    private fun setObservers(){
        viewModel.book.observe(viewLifecycleOwner){
            binding.textViewTitleBook.text = it.title
            binding.textViewAuthorValue.text = it.author
            binding.textViewGenreValue.text = it.genre
            binding.checkoxFavorite.isChecked = it.favorite

//            so aqui e criado o livro que quero usar a cor especifica
            setBackgroundOnGenreDetail(it.genre)
        }
    }

    private fun arrowBack(){
        binding.imageViewArrowBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setBackgroundOnGenreDetail(genre: String){
        val TERROR = "Terror"
        val ROMANCE = "Romance"
        val FANTASIA = "Fantasia"
        when(genre){
            TERROR -> binding.textViewGenreValue.setBackgroundResource(R.drawable.rounded_label_teal)
            ROMANCE -> binding.textViewGenreValue.setBackgroundResource(R.drawable.rounded_label_fantasy)
            FANTASIA -> binding.textViewGenreValue.setBackgroundResource(R.drawable.rounded_label_red)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}