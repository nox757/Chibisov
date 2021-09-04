package ru.chibisovap.chibisov.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.chibisovap.chibisov.utils.Status
import ru.chibisovap.chibisov.data.model.GifModel
import ru.chibisovap.chibisov.databinding.FragmentCardGifBinding
import ru.chibisovap.chibisov.utils.GifLoader
import ru.chibisovap.chibisov.utils.GlideGifLoader

class GifFragment : Fragment() {

    private val viewModel: GifViewModel by viewModels()

    private lateinit var gifLoader: GifLoader

    private var _binding: FragmentCardGifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        gifLoader = GlideGifLoader()

        viewModel.getGif().observe(viewLifecycleOwner, { resources  ->
            when (resources.status) {
                Status.LOADING -> {
                    binding.progressCircular.root.visibility = View.VISIBLE
                    binding.gifGroup.visibility = View.GONE
                    binding.errorConnection.root.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.gifGroup.visibility = View.VISIBLE
                    binding.errorConnection.root.visibility = View.GONE
                    binding.progressCircular.root.visibility = View.VISIBLE
                    val gifModel : GifModel = resources.data!!
                    binding.captionTextView.text = gifModel.description
                    gifLoader.load(
                        gifModel.gifURL,
                        binding.gifView,
                        onLoadingFailed = { binding.gifGroup.visibility = View.GONE },
                        onLoadingFinished = {  binding.progressCircular.root.visibility = View.GONE }
                    )

                }
                Status.ERROR -> {
                    binding.gifGroup.visibility = View.GONE
                    binding.progressCircular.root.visibility = View.GONE
                    binding.errorConnection.root.visibility = View.VISIBLE
                }
            }
        })


        binding.nextBtn.setOnClickListener {
            viewModel.getNextGif()
        }

        binding.prevBtn.setOnClickListener {
            viewModel.getPrevGif()
        }
    }

}