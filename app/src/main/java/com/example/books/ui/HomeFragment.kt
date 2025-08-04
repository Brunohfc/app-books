package com.example.books.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.books.R
import com.example.books.databinding.FragmentHomeBinding
import com.example.books.helpers.BookConstants
import com.example.books.ui.adapters.BookAdapter
import com.example.books.ui.listeners.BookListener
import com.example.books.viewModels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    delegando instanciacao da VM para o androidx
    private val homeViewModel: HomeViewModel by viewModels()

    private val adapter : BookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//       1. inflar elemento recyclerView
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        2.criar o layout manager
        binding.recyclerBooks.layoutManager = LinearLayoutManager(context)

//      3. criacao do adapter
        binding.recyclerBooks.adapter = adapter

//        buscando os dados no repository
        homeViewModel.getBooks()
        setObserve() // observando as mudanca na homeView

        attachListener()

        return binding.root
    }

//    quando iniciar o fragment sempre vai pegar o mais recente
    override fun onResume() {
        super.onResume()
        homeViewModel.getBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserve(){
        homeViewModel.books.observe(viewLifecycleOwner){
            adapter.updateBooks(it)
        }
    }

    private fun attachListener(){
        adapter.attachListener(object : BookListener{
            override fun onClick(id: Int) {
//                mandando o id do livro para o fragment especifico
                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID, id)

                findNavController().navigate(R.id.navigation_details, bundle)

            }

            override fun onFavoriteClick(id: Int) {
                homeViewModel.favorite(id)
                homeViewModel.getBooks()
            }

        })
    }
}