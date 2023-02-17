package com.judahben149.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.judahben149.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.customView.toggleColors()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.secondView.reInitializeCanvasParameters()
    }
}