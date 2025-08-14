package com.example.note.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.R
import com.example.note.databinding.FragmentHomeBinding
import com.example.note.presentation.UiState
import com.example.note.presentation.create.CreateFragment
import com.example.note.presentation.model.NoteModel
import com.example.note.presentation.model.toModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()
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

        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter

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

        initView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.notes.observe(viewLifecycleOwner) { note ->
            adapter.submitList(note)
        }
    }

    private fun initView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter

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
            if (viewModel.sort) {
                viewModel.getAllNoteAscending()
            } else {
                viewModel.getAllNote()
            }
        }

        binding.btnDate.setOnClickListener {
            viewModel.getAllNote()
        }

        binding.btnAscending.setOnClickListener {
            viewModel.getAllNoteAscending()
        }

        viewModel.getAllNote()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}