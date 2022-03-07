package com.example.misanimes

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.misanimes.databinding.ActivityDetailsBinding
import com.example.misanimes.databinding.ActivityInsertBinding
import com.example.misanimes.db.DBAnimes
import com.example.misanimes.model.Anime

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var dbAnimes: DBAnimes
    var anime: Anime? = null
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            val bundle = intent.extras
            if (bundle != null){
                id = bundle.getInt("ID",0)
            }else{
                id = savedInstanceState?.getSerializable("ID") as Int

            }
            dbAnimes = DBAnimes(this)
            anime = dbAnimes.getAnime(id)
            if (anime != null){
                with(binding){
                    tvNombre.setText(anime?.titulo)
                    tvTemporada.setText(anime?.temporadas.toString())
                    when(anime?.genero){
                        0->{
                            Toast.makeText(this@DetailsActivity,getString(R.string.Error),Toast.LENGTH_LONG).show()
                        }
                        1->{
                            ivDetalle.setImageResource(R.drawable.comedia)
                            tvGenero.setText(getString(R.string.comedia))
                        }
                        2->{
                            ivDetalle.setImageResource(R.drawable.love)
                            tvGenero.setText(getString(R.string.romance))
                        }
                        3->{
                            ivDetalle.setImageResource(R.drawable.aventura)
                            tvGenero.setText(getString(R.string.aventura))
                        }
                    }

                }
            }
        }

    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit->{
                val intent = Intent (this,EditActivity::class.java)
                intent.putExtra("ID2", id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete->{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.confirmacion))
                    .setMessage(getString(R.string.pregunta, anime!!.titulo))
                    .setPositiveButton(getString(R.string.si), DialogInterface.OnClickListener { dialogInterface, i ->
                        if (dbAnimes.deleteAnime(id)){
                            Toast.makeText(this,getString(R.string.eliminado),Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                    })
                    .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialogInterface, i ->  })
                    .show()

            }
            R.id.btnRegresar->{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}