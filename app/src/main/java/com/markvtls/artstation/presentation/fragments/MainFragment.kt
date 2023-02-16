package com.markvtls.artstation.presentation.fragments


import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.markvtls.artstation.data.Constants.LOADING_ERROR_MESSAGE
import com.markvtls.artstation.databinding.FragmentMainBinding
import com.markvtls.artstation.domain.model.Image
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment() {


    private val viewModel: MainViewModel by viewModels()


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {

            mainImageChanges.start()
            favoritesChanges.start()

            eventsFlow
                .onEach {
                    when(it) {
                        MainViewModel.Event.LoadingError -> {
                            showErrorSnackbar()
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                currentImage.observe(viewLifecycleOwner) {
                    enableFavoritesButton(it)
                    Glide.with(this@MainFragment)
                        .load(it.url.toUri())
                        .placeholder(ColorDrawable(Color.GRAY))
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.image)
                    binding.description.text = it.title
                }
            }

        }

    }

    /**Use this to handle "Mark as favorite" button clicks*/
    private fun enableFavoritesButton(image: Image) {
        binding.addToFavorites.setOnClickListener {
            viewModel.addNewImageToFavorites(image)
        }
    }


    /**Use this to notify user about connection issues*/
    private fun showErrorSnackbar() {
        val snackbar = Snackbar.make(binding.image, LOADING_ERROR_MESSAGE, Snackbar.LENGTH_LONG)
        snackbar.show()
    }


    }
