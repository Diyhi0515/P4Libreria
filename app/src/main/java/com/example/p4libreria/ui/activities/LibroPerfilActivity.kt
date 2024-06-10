package com.example.p4libreria.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.p4libreria.R
import com.example.p4libreria.databinding.ActivityLibroPerfilBinding
import com.example.p4libreria.databinding.LibroPerfilBinding
import com.example.p4libreria.ui.viewmodels.LibroPerfilViewModel

class LibroPerfilActivity : AppCompatActivity() {
    private var id: Int = 0

    lateinit var binding: ActivityLibroPerfilBinding
    private val model: LibroPerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLibroPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
        setupViewModelObservers()

        id = intent.getIntExtra("libroId", -1)
        if (id != -1) {
            model.buscarLibro(id)
        }
        Log.d("idLP", id.toString())
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
        model.librito.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.txtNombreLibroPerfil.text = it.nombre
            binding.txtAutor.text = it.autor
            if (it.imagen != null) {
                try{
                    Log.d("imagen", it.imagen)
                    Glide.with(this)
                        .load(it.imagen)
                        .into(binding.imgLibroPerfil)
                }catch (e: Exception){
                    Log.d("error", "no se puede cargar la imagen")
                }
            }
            binding.txtEditorial.text = it.editorial
            binding.txtAutor.text = it.autor
            binding.txtSinopsis.text = it.sinopsis
            binding.textCalificaionPerfil.text = it.calificacion.toString()
        }
    }

    private fun setupEventListeners() {
        binding.btnEditarDatosLibro.setOnClickListener {
            val intent = Intent(this, LibroDetailActivity::class.java)
            intent.putExtra("libroId", id)
            startActivity(intent)
        }
        binding.btnEliminar.setOnClickListener {
            model.deleteLibro(id)
        }
        binding.btnVerGeneros.setOnClickListener {
            val intent = Intent(this, LibroGenerosActivity::class.java)
            intent.putExtra("libroId", id)
            startActivity(intent)
        }
    }

}