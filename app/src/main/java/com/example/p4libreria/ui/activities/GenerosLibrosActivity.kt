package com.example.p4libreria.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p4libreria.R
import com.example.p4libreria.databinding.ActivityGenerosLibrosBinding
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.Libros
import com.example.p4libreria.ui.adapters.GeneroLibroAdapter
import com.example.p4libreria.ui.viewmodels.LibroGeneroViewModel

class GenerosLibrosActivity : AppCompatActivity(), GeneroLibroAdapter.OnGeneroLibroClickListener {
    lateinit var binding: ActivityGenerosLibrosBinding
    private val model: LibroGeneroViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerosLibrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.generoLibroList.apply {
            this.adapter = GeneroLibroAdapter(Libros(), this@GenerosLibrosActivity)
            layoutManager = LinearLayoutManager(this@GenerosLibrosActivity)
        }


        val generoId = intent.getIntExtra("generoId", -1)
        Log.d("GenerosLibrosActivity", "GeneroId: $generoId")

        model.cargarLibros()


        model.librosList.observe(this) {
            model.getBooksByGenre(generoId)
        }

        model.librosG.observe(this) { libros ->
            libros?.let {
                val adapter = (binding.generoLibroList.adapter as GeneroLibroAdapter)
                adapter.updateData(it as Libros)
            }
        }

    }

    override fun onGeneroClick(libro: Libro) {
        val intent = Intent(this, LibroPerfilActivity::class.java)
        intent.putExtra("libroId", libro.id)
        startActivity(intent)
        Log.d("Se encontro Id", "Libro seleccionado: ${libro.id}")
    }
}

