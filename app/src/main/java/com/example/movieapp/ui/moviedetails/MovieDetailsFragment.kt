package com.example.movieapp.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarDetails)
        binding.toolbarDetails.setNavigationIcon(R.drawable.ic_back)
        binding.toolbarDetails.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    fun observeData(){
        viewLifecycleOwner.lifecycleScope.launch {

            val bundle: MovieDetailsFragmentArgs by navArgs()
            movieDetailsViewModel.movieID = bundle.movieID

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.viewState.collectLatest { movieViewState ->
                    movieViewState.consumableErrors?.let { consumableError ->
                        consumableError.firstOrNull()?.let { error ->
                            Toast.makeText(context, error.exception, Toast.LENGTH_SHORT).show()
                            movieDetailsViewModel.errorConsumed(error.id)
                        }
                    }

                    binding.isLoading = movieViewState.isLoading

                    if (movieViewState.movie != null) {
                        binding.movie = movieViewState.movie
                        checkFavorites(movieViewState.movie.imdbID)
                    }
                }
            }
        }
    }

    suspend fun checkFavorites(movieID: String){
        viewLifecycleOwner.lifecycleScope.launch{
            if(movieDetailsViewModel.checkFavorites(movieID)){
                binding.isFavorites = true

                binding.ibFavorites.setOnClickListener{
                    movieDetailsViewModel.removeFromFavorites(movieID)
                }

            }else{
                binding.isFavorites = false

                binding.ibFavorites.setOnClickListener{
                    movieDetailsViewModel.addToFavorites(movieID)
                }
            }
            checkFavorites(movieID)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}