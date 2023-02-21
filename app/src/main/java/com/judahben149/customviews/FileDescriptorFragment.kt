package com.judahben149.customviews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.judahben149.customviews.databinding.ActivityMainBinding
import com.judahben149.customviews.databinding.FragmentFileDescriptorBinding

class FileDescriptorFragment : Fragment() {

    private var _binding: FragmentFileDescriptorBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileDescriptorBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            selectFile()
        }
        return binding.root
    }

    private fun selectFile() {
        val selectFileIntent = Intent(Intent.ACTION_GET_CONTENT)
        selectFileIntent.type = "*/*"
        startActivityForResult(selectFileIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val fileUri = data.data

                binding.fileDescriptor.fileUri = fileUri
            }
        }
    }

}