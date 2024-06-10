package com.example.p4libreria.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.p4libreria.databinding.LibrogeneroItemBinding
import com.example.p4libreria.models.Genero
import com.example.p4libreria.models.Generos

class LibroGeneroAdapter(val GeneroList: Generos, val listener: OnLibroGeneroClickListener ) :
    RecyclerView.Adapter<LibroGeneroAdapter.LibroGeneroViewHolder>() {

    var generosId = ArrayList<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroGeneroViewHolder {
        val binding =
            LibrogeneroItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LibroGeneroViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return GeneroList.size
    }

    override fun onBindViewHolder(holder: LibroGeneroViewHolder, position: Int) {
        val genero = GeneroList[position]
        holder.bind(genero, generosId, listener)
    }

    fun updateData(newGeneroList: List<Genero>, generosId: ArrayList<Int>) {
        GeneroList.clear()
        this.generosId = generosId
        GeneroList.addAll(newGeneroList)
        notifyDataSetChanged()
    }

    class LibroGeneroViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genero: Genero, generosId: ArrayList<Int>, listener: OnLibroGeneroClickListener) {
            val binding = LibrogeneroItemBinding.bind(itemView)
            binding.apply {
                btntoggleGeneroLibro.id = genero.id!!
                btntoggleGeneroLibro.text =genero.nombre
                btntoggleGeneroLibro.textOn = genero.nombre
                btntoggleGeneroLibro.textOff = genero.nombre

                btntoggleGeneroLibro.setOnCheckedChangeListener { buttonView, isChecked ->
                    listener.onLibroGeneroClick(genero)
                }
                if (generosId.contains(genero.id)) {
                    btntoggleGeneroLibro.isChecked = true
                }

            }

        }
    }

    interface OnLibroGeneroClickListener {
        fun onLibroGeneroClick(genero: Genero)

    }


}