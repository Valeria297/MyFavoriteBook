package com.example.databasescoroutines.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.databasescoroutines.databinding.FragmentRegistrationBinding
import com.example.databasescoroutines.model.RoomBook
import com.example.databasescoroutines.room.AppDatabase

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    private var count = 0

    private val database: AppDatabase by lazy {
        Room
            .databaseBuilder(
                requireContext().applicationContext,
                AppDatabase::class.java,
                "room-book-db"
            )
            .allowMainThreadQueries()
            .build()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_KEY, count)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentRegistrationBinding.inflate(layoutInflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            addButton.setOnClickListener {
                val authorName = authorName.text.toString().plus(" - ")
                val bookTitle = bookTitle.text.toString()

                database.roomBookDao().insertBooks(
                    RoomBook(author = authorName, title = bookTitle)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val COUNT_KEY = "COUNT_KEY"
    }
}
