package com.example.misanimes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.misanimes.databinding.ActivityInsertBinding
import com.example.misanimes.db.DBAnimes

class InsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding
    private var spinnerPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listaOpciones = resources.getStringArray(R.array.generos)
        val generos = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, listaOpciones
        )


        with(binding) {
            spFormulas.adapter = generos
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
                        Toast.makeText(this, getString(R.string.Error), Toast.LENGTH_LONG).show()
                    } else {
                        val dbAnimes = DBAnimes(this)
                        with(binding) {
                            val id = dbAnimes.insertAnime(
                                etValor1.text.toString(),
                                etValor2.text.toString().toInt(),
                                spinnerPosition
                            )
                            if (id > 0) {
                                Toast.makeText(
                                    this@InsertActivity,
                                    getString(R.string.guardado),
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Toast.makeText(
                                    this@InsertActivity,
                                    getString(R.string.Error),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            intent = Intent(this@InsertActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            R.id.btnCancel -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}