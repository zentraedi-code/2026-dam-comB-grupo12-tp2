package com.dam.tp2dam.app.ui.clientes

import Cliente
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.SQLiteHelper
import com.dam.tp2dam.app.ui.panel.control.PanelControlActivity
import com.google.android.material.card.MaterialCardView

class ClienteActivity : AppCompatActivity() {

    private val listaClientes = mutableListOf<Cliente>()
    private lateinit var adapter: ClientesAdapter
    private lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        helper = SQLiteHelper(this)

        configurarRecyclerView()
        configurarBuscador()
        configurarAgregar()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        adapter.cargarClientes()
    }

    private fun setupClickListeners() {
        findViewById<android.widget.ImageView>(R.id.btnBack).setOnClickListener {
            startActivity(Intent(this, PanelControlActivity::class.java))
        }
    }

    private fun configurarRecyclerView() {
        adapter = ClientesAdapter(listaClientes, helper)

        val rvClientes = findViewById<RecyclerView>(R.id.rvClientes)
        rvClientes.layoutManager = LinearLayoutManager(this)
        rvClientes.adapter = adapter

        adapter.cargarClientes()
    }

    private fun configurarBuscador() {
        val etBuscar = findViewById<EditText>(R.id.etBuscarCliente)

        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrarClientes(s.toString())
            }
        })
    }

    private fun configurarAgregar() {
        val cardClientes = findViewById<MaterialCardView>(R.id.cardClientes)

        cardClientes.setOnClickListener {
            startActivity(Intent(this, RegistroClienteActivity::class.java))
        }
    }

    private fun filtrarClientes(texto: String) {
        val clienteDao = helper.getClienteDao()
        val clientesDB = clienteDao.obtenerTodos()

        listaClientes.clear()

        if (texto.isEmpty()) {
            listaClientes.addAll(clientesDB)
        } else {
            listaClientes.addAll(
                clientesDB.filter { it.dni.contains(texto) }
            )
        }

        adapter.notifyDataSetChanged()
    }
}
