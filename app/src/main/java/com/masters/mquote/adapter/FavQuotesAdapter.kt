package com.masters.mquote.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.masters.mquote.R
import com.masters.mquote.objects.PrefsHelper

class FavQuotesAdapter(
    private var quotes: List<String>,
    private val context: Context
) : RecyclerView.Adapter<FavQuotesAdapter.FavQuoteViewHolder>() {

    inner class FavQuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQuote: TextView = itemView.findViewById(R.id.tvQuote)
        val btnRemove: ImageView = itemView.findViewById(R.id.fav_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavQuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_list, parent, false)
        return FavQuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavQuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.tvQuote.text = quote

        holder.btnRemove.setOnClickListener {
            PrefsHelper.removeFavQuote(context, quote)
            Toast.makeText(context, "Removed from Favorites ❌", Toast.LENGTH_SHORT).show()

            quotes = PrefsHelper.getFavQuotes(context)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = quotes.size
}
