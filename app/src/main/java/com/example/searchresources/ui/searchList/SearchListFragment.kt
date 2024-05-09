package com.example.searchresources.ui.searchList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.searchresources.databinding.FragmentSearchListBinding

class SearchListFragment : Fragment() {
    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSearchItem.adapter = SearchListAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}