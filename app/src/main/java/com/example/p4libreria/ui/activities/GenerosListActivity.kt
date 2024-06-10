package com.example.p4libreria.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.p4libreria.R
import com.example.p4libreria.databinding.ActivityGenerosListBinding
import com.example.p4libreria.models.Genero
import com.example.p4libreria.models.Generos
import com.example.p4libreria.ui.adapters.GeneroAdapter
import com.example.p4libreria.ui.viewmodels.GenerosViewModel
import com.example.p4libreria.ui.viewmodels.MainViewModel

class GenerosListActivity : AppCompatActivity(), GeneroAdapter.OnGeneroClickListener{
    lateinit var binding: ActivityGenerosListBinding
    private val model: GenerosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityGenerosListBinding.inflate(layoutInflater)
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
        model.buscarGeneros()
    }

    private fun setupEventListeners() {
        binding.btnVerLibgros.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnAgregarNuevoGenero.setOnClickListener {
            val intent = Intent(this, GeneroDetailActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupViewModelListeners() {
        model.generosList.observe(this) {
            val adapter = (binding.lstGeneros.adapter as GeneroAdapter)
            adapter.updateData(it)
        }
    }


    private fun setupRecyclerView() {
        binding.lstGeneros.apply {
            this.adapter = GeneroAdapter(Generos(), this@GenerosListActivity)
            layoutManager = LinearLayoutManager(this@GenerosListActivity)
        }
    }




    override fun onGeneroClick(genero: Genero) {
        val intent = Intent(this, GenerosLibrosActivity::class.java)
        intent.putExtra("generoId", genero.id)
        startActivity(intent)
    }
}