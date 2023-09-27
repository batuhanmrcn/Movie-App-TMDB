package com.example.movieapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movieapp.MainActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.util.loadImage

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root

        viewModel.getMovieDetail(movieId = args.movieId)

        observeEvents()
    }

    private fun observeEvents() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBarDetail.isVisible = it
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            binding.textViewErrorDetail.text = it
            binding.textViewErrorDetail.isVisible = true
        }
        viewModel.movieResponse.observe(viewLifecycleOwner){ movie->
            binding.imageViewDetail.loadImage(movie.backdropPath)

            binding.textViewDetailsVote.text = movie.voteAverage.toString()
            binding.textViewDetailStudio.text = movie.productionCompanies?.first()?.name
            binding.textViewDetailLanguage.text = movie.spokenLanguages?.first()?.englishName

            binding.textViewDetailOverview.text = movie.overview

            binding.movieDetailGroup.isVisible = true

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}