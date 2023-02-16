package com.markvtls.artstation.presentation.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.markvtls.artstation.databinding.FragmentFavoritesBinding
import com.markvtls.artstation.domain.model.Image
import com.markvtls.artstation.presentation.adapters.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {


    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.favorites.observe(viewLifecycleOwner) {
                loadFavorites(it)
            }
        }
    }


    /**Loading favorites list using RecyclerView*/
    private fun loadFavorites(favorites: List<Image>) {
        val recycler = binding.favorites

        val adapter = FavoritesAdapter(requireContext()) {
            deleteFavorite(it)
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        adapter.submitList(favorites)
    }

    /**Pass this to RecyclerViewAdapter for enabling ObjectDelete button*/
    private fun deleteFavorite(id: String) {
        viewModel.deleteImageById(id)
    }
}