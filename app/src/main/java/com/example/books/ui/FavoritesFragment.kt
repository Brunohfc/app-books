package com.example.books.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.books.R
import com.example.books.databinding.FragmentFavoritesBinding
import com.example.books.helpers.BookConstants
import com.example.books.ui.adapters.BookAdapter
import com.example.books.ui.listeners.BookListener
import com.example.books.viewModels.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!


    //    delegando instanciacao da VM para o androidx
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val adapter : BookAdapter = BookAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        // criando o adapter
        binding.recyclerFavoriteBooks.layoutManager = LinearLayoutManager(context)
        binding.recyclerFavoriteBooks.adapter = adapter

        attachListener()
        setObserve()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getFavoriteBook()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserve(){
        favoritesViewModel.books.observe(viewLifecycleOwner){
            adapter.updateBooks(it)
        }
    }

    private fun attachListener(){
        adapter.attachListener(object : BookListener {
            override fun onClick(id: Int) {
//                mandando o id do livro para o fragment especifico
                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)

                findNavController().navigate(R.id.navigation_details, bundle)

            }

            override fun onFavoriteClick(id: Int) {
                favoritesViewModel.favorite(id)
                favoritesViewModel.getFavoriteBook()
            }

        })
    }
}