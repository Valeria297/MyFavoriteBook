package com.example.databasescoroutines.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.databasescoroutines.interaction.SwipeToDelete
import com.example.databasescoroutines.adapter.BookAdapter
import com.example.databasescoroutines.databinding.FragmentRoomListBinding
import com.example.databasescoroutines.model.RoomBook
import com.example.databasescoroutines.room.AppDatabase
import com.google.android.material.snackbar.Snackbar
import java.util.*

class RoomFragment : Fragment() {

    private var _binding: FragmentRoomListBinding? = null
    private val binding get() = requireNotNull(_binding)

    companion object {
        const val COUNT_KEY = "COUNT_KEY"
    }
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
        return FragmentRoomListBinding.inflate(layoutInflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBookRow()
    }

    private fun showBookRow() {
        with(binding) {
            ifRoomIsEmpty()

            val linearLayoutManager = LinearLayoutManager(
              view?.context, LinearLayoutManager.VERTICAL, false
            )
            val list = database.roomBookDao().getAll()
            val adapter = BookAdapter(requireContext(), list as MutableList<RoomBook>)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager

            val swipeToDelete = object : SwipeToDelete() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    val temp = list[pos]

                    list.removeAt(pos)
                    database.roomBookDao().delete(temp)

                    adapter.notifyItemRemoved(pos)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeToDelete)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }

    private fun ifRoomIsEmpty() {
        if (database.roomBookDao().equals(null)) {
            Snackbar.make (requireNotNull(view), "List is empty",
                Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}