package com.example.note.presentation.create

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.note.databinding.FragmentCreateBinding
import com.example.note.presentation.main.MainActivity.Companion.preferences
import com.example.note.presentation.model.NoteModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class CreateFragment : Fragment() {
    private var note: NoteModel? = null
    private var _binding : FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private val viewModel : CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("note", NoteModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable("note")
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if (note != null) {
            binding.etTitle.setText(note!!.title)
            binding.etContent.setText(note!!.content)
            binding.textView2.text = "메모 수정"
        }

        binding.btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnCreate.setOnClickListener {
            if (note != null) {
                viewModel.updateNote(
                    NoteModel(
                        id = note!!.id,
                        title = binding.etTitle.text.toString(),
                        content = binding.etContent.text.toString(),
                        createdDate = getFormattedDate()
                    )
                )
            }
            else {
                viewModel.createNote(
                    NoteModel(
                        id = preferences.getId("note_id", 1),
                        title = binding.etTitle.text.toString(),
                        content = binding.etContent.text.toString(),
                        createdDate = getFormattedDate()
                    )
                )
            }

            parentFragmentManager.popBackStack()
        }
    }

    private fun getFormattedDate() : String {
        val format = SimpleDateFormat("yyyyMMddhhmmss", Locale.KOREA)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return format.format(Date().time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}