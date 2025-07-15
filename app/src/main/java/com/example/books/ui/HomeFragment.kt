package com.example.books.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.books.databinding.FragmentHomeBinding
import com.example.books.ui.adapters.BookAdapter
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

        return binding.root
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
}