package com.example.databasescoroutines.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.databasescoroutines.databinding.ItemModelBinding
import com.example.databasescoroutines.model.RoomBook

class BookViewHolder(
    private val binding: ItemModelBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: RoomBook) {
        with(binding) {
            textView.text = book.author.plus(book.title)
        }
    }
}