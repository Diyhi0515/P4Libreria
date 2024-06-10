package com.example.p4libreria.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.p4libreria.R
import com.example.p4libreria.databinding.ActivityLibroDetailBinding
import com.example.p4libreria.ui.viewmodels.LibroDetailViewModel
class LibroDetailActivity : AppCompatActivity() {
    private var id: Int = 0
    lateinit var binding: ActivityLibroDetailBinding
    private val model: LibroDetailViewModel by viewModels()

    private var generosId = ArrayList<Int>()
    private val REQUEST_CODE = 1
    private var generosNew : ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLibroDetailBinding.inflate(layoutInflater)
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
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }

        model.editarGeneros.observe(this) {
            if (it) {
                if (id != -1) {
                    Log.d("LibroSaveActivity", "Entro al editar generos")
                    model.editarGeneros(generosId, generosNew, id)
                }else{
                    Log.d("LibroSaveActivity", "Entro al lastId")
                    model.getLastId()
                }
            }
        }

        model.librosList.observe(this) {
            if (it != null) {

                id = it[it.size - 1].id!!
                Log.d("LibroSaveActivity", id.toString())

                model.editarGeneros(generosId, generosNew,id)
            }
        }

        model.libro.observe(this) {
            if (it == null) {
                return@observe
            }

            binding.txlibroNameName.editText?.setText(it.nombre)
            binding.txtAutor.editText?.setText(it.autor)
            binding.txtEditorial.editText?.setText(it.editorial)
            binding.txtImagen.editText?.setText(it.imagen)
            binding.txtSinopsis.editText?.setText(it.sinopsis)
            binding.txtCalificaion.editText?.setText(it.calificacion.toString())
            binding.txtIsbn.editText?.setText(it.isbn)

            for (genero in it.generos) {
                generosId.add(genero.id!!)
                Log.d("LibroSaveActivity", genero.id.toString())
            }
        }

    }

    private fun setupEventListeners() {
        binding.btnGuardarLibro.setOnClickListener {
            model.saveLibro(
                binding.txlibroNameName.editText?.text.toString(),
                binding.txtAutor.editText?.text.toString(),
                binding.txtEditorial.editText?.text.toString(),
                binding.txtIsbn.editText?.text.toString(),
                binding.txtImagen.editText?.text.toString(),
                binding.txtSinopsis.editText?.text.toString(),
                binding.txtCalificaion.editText?.text.toString().toInt(), id
            )
        }
        binding.btnAgregarGeneros.setOnClickListener {
            val intent = Intent(this, LibroGenerosActivity::class.java)
            intent.putExtra("libroId", id)
            startActivity(intent)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            generosNew = data?.getIntegerArrayListExtra("libroId")
            Log.d("generos", generosNew.toString())
        }
    }
}
