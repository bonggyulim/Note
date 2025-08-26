package com.example.note.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.R
import com.example.note.databinding.FragmentHomeBinding
import com.example.note.presentation.UiState
import com.example.note.presentation.create.CreateFragment
import com.example.note.presentation.create.model.NoteModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = NotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeNotes()
        viewModel.getAllNote()
    }

    private fun initViews() {
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        adapter.editClick = object : NotesAdapter.EditClick {
            override fun editClick(noteModel: NoteModel) {
                val createFragment = CreateFragment().apply {
                    arguments = bundleOf("note" to noteModel)
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, createFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        adapter.deleteClick = object : NotesAdapter.DeleteClick {
            override fun deleteClick(noteModel: NoteModel) {
                viewModel.deleteNote(noteModel)
            }
        }

        binding.btnCreate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnRe.setOnClickListener {
            // 현재 정렬 상태에 맞춰 다시 로드
            if (viewModel.sort) viewModel.getAllNoteAscending()
            else viewModel.getAllNote()
        }

        binding.btnDate.setOnClickListener {
            // 날짜 기준(기본 서버 정렬)로
            viewModel.getAllNote()
        }

        binding.btnAscending.setOnClickListener {
            viewModel.getAllNoteAscending()
        }
    }

    private fun observeNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notesState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        //No action needed
                    }
                    is UiState.Success -> {
                        adapter.submitList(state.data)
                    }
                    is UiState.Error -> {
                        Toast.makeText(requireActivity(), state.message , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
