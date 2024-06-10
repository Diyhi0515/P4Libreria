package com.example.p4libreria.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.p4libreria.databinding.LibroItemBinding
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.Libros

class LibroAdapter(val libroList: Libros,val listener: OnLibroClickListener
    ) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>(){

    interface OnLibroClickListener {
        fun onLibroClick(libro: Libro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val binding =
            LibroItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LibroViewHolder(binding.root)
    }
    override fun getItemCount(): Int {
        return libroList.size
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libroList[position]
        holder.bind(libro, listener)
    }
    fun updateData(libroList: Libros) {
        this.libroList.clear()
        this.libroList.addAll(libroList)
        notifyDataSetChanged()
        Log.d("libroList", libroList.toString())
    }
    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(libro: Libro, listener: OnLibroClickListener) {
            val binding = LibroItemBinding.bind(itemView)
            binding.apply {
                txtNombreLibro.text = libro.nombre
                txtCalificaion.text = libro.calificacion.toString()
                if (libro.imagen != null) {
                    try{
                        Log.d("imagen", libro.imagen)
                        Glide.with(itemView)
                            .load(libro.imagen)
                            .into(imgLibro)
                    }catch (e: Exception){
                        Log.d("error", "no se puede cargar la imagen")
                    }
                }
                root.setOnClickListener { listener.onLibroClick(libro) }
            }


        }
    }
}