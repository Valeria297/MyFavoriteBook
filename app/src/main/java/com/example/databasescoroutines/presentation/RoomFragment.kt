package com.example.databasescoroutines.presentation

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
import com.example.databasescoroutines.model.adapter.BookAdapter
import com.example.databasescoroutines.databinding.FragmentRoomListBinding
import com.example.databasescoroutines.model.RoomBook
import com.example.databasescoroutines.room.AppDatabase

class RoomFragment : Fragment() {

    private var _binding: FragmentRoomListBinding? = null
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
        return FragmentRoomListBinding.inflate(layoutInflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBooks()
    }

    private fun showBooks() {
        with(binding) {

            val linearLayoutManager = LinearLayoutManager(
              view?.context, LinearLayoutManager.VERTICAL, false
            )
            val list = database.roomBookDao().getAll()
            val adapter = BookAdapter(list as MutableList<RoomBook>)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val COUNT_KEY = "COUNT_KEY"
    }
}