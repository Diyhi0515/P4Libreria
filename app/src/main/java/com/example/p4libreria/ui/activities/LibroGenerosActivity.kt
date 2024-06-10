package com.example.p4libreria.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.p4libreria.R
import com.example.p4libreria.databinding.ActivityLibroGenerosBinding
import com.example.p4libreria.models.Genero
import com.example.p4libreria.ui.adapters.LibroGeneroAdapter
import com.example.p4libreria.ui.viewmodels.GeneroAlibro

class LibroGenerosActivity : AppCompatActivity(), LibroGeneroAdapter.OnLibroGeneroClickListener{
    lateinit var binding: ActivityLibroGenerosBinding
    val model : GeneroAlibro by viewModels()
    var generosId = ArrayList<Int>()
    var generosIdIntent = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLibroGenerosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupViewModelListeners()
        setupEventListeners()
        generosId = intent.getIntegerArrayListExtra("libroId") as ArrayList<Int>

        model.buscarListaGeneros()
    }

    private fun setupEventListeners() {
        binding.btnGuardarGenero.setOnClickListener {
            Log.d("LibroSaveGeneroActivity", "generosId: $generosIdIntent")
            val intent = intent
            intent.putIntegerArrayListExtra("libroId", generosIdIntent)
            setResult(RESULT_OK, intent)
            finish()
        }

    }


    private fun setupViewModelListeners() {

        model.generosList.observe(this) {
            val adapter = (binding.lstLibroGeneros.adapter as LibroGeneroAdapter)
            adapter.updateData(it, generosId)
        }
    }

    private fun setupRecyclerView() {
        binding.lstLibroGeneros .apply {
            this.adapter = LibroGeneroAdapter(ArrayList(), this@LibroGenerosActivity)
            layoutManager = GridLayoutManager(this@LibroGenerosActivity,2)
        }
    }

    override fun onLibroGeneroClick(genero: Genero) {
        Log.d("LibroSaveGeneroActivity", "genero: ${genero.id}")
        if (generosIdIntent.contains(genero.id)) {
            generosIdIntent.remove(genero.id)
        } else {
            generosIdIntent.add(genero.id!!)
        }
    }
}