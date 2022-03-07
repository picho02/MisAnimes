package com.example.misanimes.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.misanimes.R
import com.example.misanimes.databinding.ElementoListaBinding
import com.example.misanimes.model.Anime

class AnimeAdapter(private val contexto: Context, val datos:ArrayList<Anime>): BaseAdapter() {
    private val layoutInflater = LayoutInflater.from(contexto)
    override fun getCount(): Int {
        return datos.size
    }

    override fun getItem(p0: Int): Any {
        return datos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return datos[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ElementoListaBinding.inflate(layoutInflater)
        with(binding){
            tvTitulo.text=datos[p0].titulo
            tvTemporadas.text=datos[p0].temporadas.toString()

            when(datos[p0].genero.toString().toInt()){
                0->{
                    ivElemento.setImageResource(R.drawable.header)
                }
                1->{
                    ivElemento.setImageResource(R.drawable.comedia)
                }
                2->{
                    ivElemento.setImageResource(R.drawable.love)
                }
                3->{
                    ivElemento.setImageResource(R.drawable.aventura)
                }

            }
        }
        return binding.root
    }
}