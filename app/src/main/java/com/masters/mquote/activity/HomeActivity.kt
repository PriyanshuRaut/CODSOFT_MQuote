package com.masters.mquote.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masters.mquote.R
import com.masters.mquote.adapter.FavQuotesAdapter
import com.masters.mquote.objects.PrefsHelper

class HomeActivity : AppCompatActivity() {

    lateinit var quote: TextView
    lateinit var fav: CardView
    lateinit var share: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        quote = findViewById(R.id.quote_txt)
        fav = findViewById(R.id.fav)
        share = findViewById(R.id.shared)
        val fabFav = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_fav)


        val quoteList = resources.getStringArray(R.array.quotes)
        var random = (0 until quoteList.size).random()
        quote.text = quoteList[random]


        fabFav.setOnClickListener {
            val currentQuote = quote.text.toString()
            PrefsHelper.saveFavQuote(this, currentQuote)
            Toast.makeText(this, "Added to Favorites ❤️", Toast.LENGTH_SHORT).show()
        }


        fav.setOnClickListener {
            showFavDialog()
        }

        share.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, quote.text.toString())
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }


        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun showFavDialog() {
        val dialogView = layoutInflater.inflate(R.layout.fav_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rvFavQuotes)
        val btnClose = dialogView.findViewById<Button>(R.id.btnClose)

        val favQuotes = PrefsHelper.getFavQuotes(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavQuotesAdapter(favQuotes,this)

        btnClose.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }
}
