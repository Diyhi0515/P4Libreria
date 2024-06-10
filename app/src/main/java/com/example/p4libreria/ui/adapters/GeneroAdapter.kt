package com.example.p4libreria.ui.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.p4libreria.databinding.GeneroItemBinding
import com.example.p4libreria.databinding.LibroItemBinding
import com.example.p4libreria.models.Genero
import com.example.p4libreria.models.Generos


class GeneroAdapter (val generoList: Generos,val listener: OnGeneroClickListener
) : RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>(){

    interface OnGeneroClickListener {
        fun onGeneroClick(genero: Genero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneroViewHolder {
        val binding =
            GeneroItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GeneroViewHolder(binding.root)
    }
    override fun getItemCount(): Int {
        return generoList.size
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val genero = generoList[position]
        holder.bind(genero, listener)
    }
    fun updateData(generoList: Generos) {
        this.generoList.clear()
        this.generoList.addAll(generoList)
        notifyDataSetChanged()
    }
    class GeneroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(genero: Genero, listener: OnGeneroClickListener) {
            val binding = GeneroItemBinding.bind(itemView)
            binding.apply {
                txtGenero.text = genero.nombre
                root.setOnClickListener { listener.onGeneroClick(genero) }
            }


        }
    }
}