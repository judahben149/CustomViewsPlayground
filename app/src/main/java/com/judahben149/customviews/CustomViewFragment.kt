package com.judahben149.customviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.judahben149.customviews.databinding.FragmentCustomViewBinding


class CustomViewFragment : Fragment() {

    var _binding: FragmentCustomViewBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomViewBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            binding.customView.toggleColors()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.secondView.reInitializeCanvasParameters()
    }
}