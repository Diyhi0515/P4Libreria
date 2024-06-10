package com.example.p4libreria.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p4libreria.R
import com.example.p4libreria.databinding.ActivityMainBinding
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.Libros
import com.example.p4libreria.ui.adapters.LibroAdapter
import com.example.p4libreria.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), LibroAdapter.OnLibroClickListener {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEventListeners()
        setupRecyclerView()
        setupViewModelListeners()
    }

    override fun onResume() {
        super.onResume()
        model.buscarLibros()
    }



    private fun setupViewModelListeners() {
        model.librosList.observe(this) {
            val adapter = (binding.lstLibros.adapter as LibroAdapter)
            adapter.updateData(it)
        }
    }


    private fun setupRecyclerView() {
        binding.lstLibros.apply {
            this.adapter = LibroAdapter(Libros(), this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupEventListeners() {
        binding.btnAgregarNuevoLibro.setOnClickListener {
            val intent = Intent(this, LibroDetailActivity::class.java)
            startActivity(intent)
        }
        binding.btnVerGeneros.setOnClickListener {
            val intent = Intent(this, GenerosListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLibroClick(libro: Libro) {
        val intent = Intent(this, LibroPerfilActivity::class.java)
        intent.putExtra("libroId", libro.id)
        startActivity(intent)
        Log.d("Se encontro Id", "Libro seleccionado: ${libro.id}")
    }
}
