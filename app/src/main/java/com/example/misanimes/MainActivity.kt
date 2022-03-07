package com.example.misanimes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.misanimes.adapter.AnimeAdapter
import com.example.misanimes.databinding.ActivityMainBinding
import com.example.misanimes.db.DBAnimes
import com.example.misanimes.model.Anime

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAnimes: ArrayList<Anime>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbAnimes = DBAnimes(this)
        listAnimes = dbAnimes.getAnimes()
        listAnimes = dbAnimes.getAnimes()
        if (listAnimes.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE

        with(binding) {
            lv.adapter = AnimeAdapter(this@MainActivity, listAnimes)
            lv.setOnItemClickListener { adapterView, view, i, l ->
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra("ID", l.toInt())
                startActivity(intent)
                finish()
            }
        }
    }

    fun click(view: View) {
        val intent = Intent(this@MainActivity, InsertActivity::class.java)
        startActivity(intent)
        finish()
    }
}