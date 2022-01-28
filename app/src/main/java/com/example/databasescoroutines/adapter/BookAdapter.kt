package com.example.databasescoroutines.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databasescoroutines.databinding.ItemModelBinding
import com.example.databasescoroutines.model.RoomBook

class BookAdapter(private val context: Context,
                  private val booksList: MutableList<RoomBook>
) : RecyclerView.Adapter<BookViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return BookViewHolder(
            binding = ItemModelBinding.inflate(layoutInflater, parent, false),
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val bookItem = booksList[position]
        holder.bind(bookItem)
    }

    override fun getItemCount(): Int {
      return booksList.size
    }

}