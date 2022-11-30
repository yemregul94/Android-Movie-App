package com.example.movieapp.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.Movie
import com.example.movieapp.databinding.MovieHeaderItemBinding
import com.example.movieapp.databinding.MovieListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListAdapter(var viewModel: MovieListViewModel, var itemList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindingMovie: MovieListItemBinding
    private lateinit var bindingPage: MovieHeaderItemBinding

    private val MOVIE = 0
    private val PAGE = 1

    override fun getItemViewType(position: Int): Int =
        when (itemList[position]) {
            is Movie -> {
                MOVIE
            }
            is Int -> {
                PAGE
            }
            else -> {
                PAGE
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MOVIE -> {
                bindingMovie = MovieListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                MoviesViewHolder(bindingMovie, viewModel)
            }
            PAGE -> {
                bindingPage = MovieHeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                PageViewHolder(bindingPage)
            }
            else -> {
                bindingPage = MovieHeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                PageViewHolder(bindingPage)
            }
        }


    override fun getItemCount(): Int {
        return itemList.size
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            MOVIE -> {
                (viewHolder as MoviesViewHolder).bind(itemList[position] as Movie )
            }
            PAGE -> {
                (viewHolder as PageViewHolder).bind(itemList[position] as Int)
            }
            else -> {
            }
        }
    }

    class PageViewHolder(private val binding: MovieHeaderItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Int) {
            binding.page = item
            binding.executePendingBindings()
        }
    }

    class MoviesViewHolder(private val binding: MovieListItemBinding, val viewModel: MovieListViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            binding.executePendingBindings()


            binding.cardItem.setOnClickListener {
                val nav = MovieListFragmentDirections.actionListToDetails(item.id)
                Navigation.findNavController(it).navigate(nav)
            }

            CoroutineScope(Dispatchers.Main).launch { //Glide nedeniyle main
                if(viewModel.checkFavorites(item.id)){
                    Glide.with(binding.ibMovieItemFav).load(R.drawable.ic_favorite).into(binding.ibMovieItemFav)

                    binding.ibMovieItemFav.setOnClickListener{
                        viewModel.removeFromFavorites(item.id)
                        bindingAdapter?.notifyItemChanged(position)
                    }

                }else{
                    Glide.with(binding.ibMovieItemFav).load(R.drawable.ic_not_favorite).into(binding.ibMovieItemFav)

                    binding.ibMovieItemFav.setOnClickListener{
                        viewModel.addToFavorites(item.id)
                        bindingAdapter?.notifyItemChanged(position)
                    }
                }
            }

        }
    }

}

