package com.example.misanimes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.misanimes.databinding.ActivityEditBinding
import com.example.misanimes.databinding.ActivityInsertBinding
import com.example.misanimes.db.DBAnimes
import com.example.misanimes.model.Anime

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var dbAnimes: DBAnimes
    var anime: Anime? = null
    var id = 0
    var spinnerPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            val bundle = intent.extras
            if (bundle != null){
                id = bundle.getInt("ID2",0)
            }else{
                id = savedInstanceState?.getSerializable("ID2") as Int

            }
            dbAnimes = DBAnimes(this)
            anime = dbAnimes.getAnime(id)
            if (anime != null){
                val listaOpciones = resources.getStringArray(R.array.generos)
                val generos = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item, listaOpciones
                )
                with(binding){
                    etValor1.setText(anime?.titulo)
                    etValor2.setText(anime?.temporadas.toString())
                    spFormulas.adapter = generos
                    when(anime?.genero){
                        0->{
                            Toast.makeText(this@EditActivity,getString(R.string.Error), Toast.LENGTH_LONG).show()
                        }
                        1->{
                            ivHeader.setImageResource(R.drawable.comedia)
                            spFormulas.setSelection(1)
                        }
                        2->{
                            ivHeader.setImageResource(R.drawable.love)
                            spFormulas.setSelection(2)

                        }
                        3->{
                            ivHeader.setImageResource(R.drawable.aventura)
                            spFormulas.setSelection(3)

                        }
                    }

                    spFormulas.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            posicion: Int,
                            p3: Long
                        ) {
                            when (posicion) {
                                0 -> {
                                    ivHeader.setImageResource(R.drawable.header)
                                    spinnerPosition = 0

                                }
                                1 -> {
                                    ivHeader.setImageResource(R.drawable.comedia)
                                    spinnerPosition = 1
                                }
                                2 -> {
                                    ivHeader.setImageResource(R.drawable.love)
                                    spinnerPosition = 2
                                }
                                3 -> {
                                    ivHeader.setImageResource(R.drawable.aventura)
                                    spinnerPosition = 3
                                }


                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }

                }
            }
        }

    }
    private fun validaCampo(): Boolean {
        if (binding.etValor1.text.toString() == "") {
            binding.etValor1.error = getString(R.string.valor_requerido)
            binding.etValor1.requestFocus()
        }
        if (binding.etValor2.text.toString() == "") {
            binding.etValor2.error = getString(R.string.valor_requerido)
            binding.etValor2.requestFocus()
        }

        return !(binding.etValor1.text.toString() == "" || binding.etValor2.text.toString() == "")
    }

    fun click(view: View) {
        when (view.id) {
            R.id.btnAdd -> {
                if (validaCampo()) {
                    if (spinnerPosition == 0) {
                        Toast.makeText(this, getString(R.string.seleccione_genero), Toast.LENGTH_LONG).show()
                    } else {
                        val dbAnimes = DBAnimes(this)
                        with(binding) {
                            if (dbAnimes.updateAnime(
                                    id,
                                    etValor1.text.toString(),
                                    etValor2.text.toString().toInt(),
                                    spinnerPosition
                                )
                            ) {
                                Toast.makeText(
                                    this@EditActivity,
                                    getString(R.string.actualizado),
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this@EditActivity, DetailsActivity::class.java)
                                intent.putExtra("ID", id)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@EditActivity,
                                    getText(R.string.Error),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            intent = Intent(this@EditActivity, DetailsActivity::class.java)
                            intent.putExtra("ID", id)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            R.id.btnCancel -> {
                intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("ID",id)
                startActivity(intent)
                finish()
            }

        }
    }
}