package com.example.movieapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val itemList: List<Any> = emptyList()
    private val movieListViewModel: MovieListViewModel by viewModels()
    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter(movieListViewModel, itemList) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.btnMovieListSearch.setOnClickListener{ searchButton() }

        observeData()
        observeScroll()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        movieListViewModel.dataChanged = false
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieListViewModel.viewState.collectLatest { movieViewState ->

                    binding.isLoading = movieViewState.isLoading

                    if(movieListViewModel.dataChanged){
                        if (movieViewState.movieResponse != null) {
                            binding.isError = false

                            movieListAdapter.itemList += movieViewState.pageNumber!!
                            movieListAdapter.itemList += movieViewState.movieResponse.movieList
                            movieListAdapter.notifyDataSetChanged()
                        }
                        else {
                            movieListAdapter.itemList = emptyList()
                            movieListAdapter.notifyDataSetChanged()
                        }
                    }

                    movieViewState.consumableErrors?.let { consumableError ->
                        consumableError.firstOrNull()?.let { error ->
                            binding.isError = true
                            binding.errorMessage = error.exception

                            movieListViewModel.errorConsumed(error.id)
                        }
                    }
                }
            }
        }
    }

    private fun observeScroll(){
        binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvMovieList.canScrollVertically(1)) {
                    movieListViewModel.loadNextPage()
                }
            }
        })
    }


    private fun searchButton(){
        viewLifecycleOwner.lifecycleScope.launch {
            binding.isLoading = true

            movieListAdapter.itemList = emptyList()
            movieListViewModel.searchQuery = binding.etMovieListSearch.text.toString()
            movieListViewModel.searchButton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}