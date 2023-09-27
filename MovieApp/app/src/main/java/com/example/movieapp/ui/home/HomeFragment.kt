package com.example.movieapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var movieAdapter : MovieAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getMovieList()
        observeEvents()

        return binding.root


    }

    private fun observeEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner){
            binding.textViewHomeError.text = it
            binding.textViewHomeError.isVisible = true
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
        viewModel.movieList.observe(viewLifecycleOwner){ list ->
            if (list.isNullOrEmpty()){
                binding.textViewHomeError.text = "Film Bulunamadı"
                binding.textViewHomeError.isVisible = true
            } else {
                movieAdapter = MovieAdapter(list , object : MovieClickListener{
                    override fun onMovieClicked(movieId: Int?) {
                        movieId?.let {
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            findNavController().navigate(action)
                        }
                    }
                })
                binding.homeRecyclerView.adapter = movieAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}