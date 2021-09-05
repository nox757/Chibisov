package ru.chibisovap.chibisov.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.chibisovap.chibisov.R
import ru.chibisovap.chibisov.data.model.GifModel
import ru.chibisovap.chibisov.utils.GifLoader
import ru.chibisovap.chibisov.utils.GlideGifLoader
import ru.chibisovap.chibisov.databinding.FragmentCardGifBinding
import ru.chibisovap.chibisov.utils.Status

class GifFragment : Fragment() {

    private val viewModel: GifViewModel by activityViewModels()

    private val gifLoader: GifLoader = GlideGifLoader()

    private var _binding: FragmentCardGifBinding? = null
    private val binding get() = _binding!!

    private lateinit var gifContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardGifBinding.inflate(inflater, container, false)
        gifContext = container!!.context
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activePrevButton(false)

        viewModel.getGif().observe(viewLifecycleOwner, { resources ->
            when (resources.status) {
                Status.LOADING -> showProgressBar()
                Status.SUCCESS -> updateUI(resources.data)
                Status.ERROR -> showError()
            }
        })

        binding.nextBtn.setOnClickListener {
            viewModel.getNextGif()
        }

        binding.prevBtn.setOnClickListener {
            viewModel.getPrevGif()
        }

        binding.errorConnection.retryBtn.setOnClickListener {
            showProgressBar()
            viewModel.getReloadGif()
        }
    }

    private fun updateUI(gifModel: GifModel?) {
        showProgressBar(false)
        binding.captionTextView.text = gifModel?.description
        gifModel?.gifURL?.let {
            gifLoader.load(
                it,
                binding.gifView,
                onLoadingFailed = { showError() },
                onLoadingFinished = {
                    binding.progressCircular.root.visibility = View.GONE
                    activePrevButton()
                }
            )
        }
    }

    private fun showProgressBar(isShow: Boolean = true) {
        binding.gifGroup.visibility = View.VISIBLE
        binding.captionTextView.text = ""
        if (isShow) {
            binding.progressCircular.root.visibility = View.VISIBLE
            binding.gifView.visibility = View.GONE
            binding.errorConnection.root.visibility = View.GONE
        } else {
            binding.progressCircular.root.visibility = View.VISIBLE
            binding.gifView.visibility = View.VISIBLE
            binding.errorConnection.root.visibility = View.GONE
        }

    }

    private fun showError() {
        binding.gifGroup.visibility = View.GONE
        binding.progressCircular.root.visibility = View.GONE
        binding.errorConnection.root.visibility = View.VISIBLE
    }

    private fun activePrevButton(isActive: Boolean = true) {
        if (isActive && viewModel.getPosition() > 0) {
            binding.prevBtn.isEnabled = true
            binding.prevBtn.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(gifContext, R.color.white))

        } else {
            binding.prevBtn.isEnabled = false
            binding.prevBtn.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(gifContext, R.color.grey))
        }
    }

}