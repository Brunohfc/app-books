package com.example.books.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        setListeners()
        setObservers()

//        recebendo o dado da home
        bookId = arguments?.getInt(BookConstants.KEY.BOOK_ID) ?: 0

//        usando a viewModel para pegar o dado do repository
        viewModel.getBookById(bookId)

        return binding.root
    }

    //    acessa o viewModel e pega os dados que vem do repository e insere na view
    private fun setObservers() {
        viewModel.book.observe(viewLifecycleOwner) {
            binding.textViewTitleBook.text = it.title
            binding.textViewAuthorValue.text = it.author
            binding.textViewGenreValue.text = it.genre
            binding.checkoxFavorite.isChecked = it.favorite

//            so aqui e criado o livro que quero usar a cor especifica
            setBackgroundOnGenreDetail(it.genre)
        }

        viewModel.bookRemove.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.msg_removed_successfully),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setListeners() {
        binding.imageViewArrowBack.setOnClickListener {
            actionBack()
        }

        binding.buttonRemoveBook.setOnClickListener {
            removeBook()
        }
    }

    private fun removeBook() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.dialog_message_delete_item))
            .setPositiveButton(getString(R.string.dialog_positive_button_yes)) { dialog, which ->
                viewModel.removeBookById(bookId)
                actionBack()
            }
            .setNegativeButton(getString(R.string.dialog_negative_button_no)) { dialog, which ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun actionBack(){
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun setBackgroundOnGenreDetail(genre: String) {
        val TERROR = "Terror"
        val ROMANCE = "Romance"
        val FANTASIA = "Fantasia"
        when (genre) {
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