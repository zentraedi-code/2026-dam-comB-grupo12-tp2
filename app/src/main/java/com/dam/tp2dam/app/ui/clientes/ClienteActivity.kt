package com.dam.tp2dam.app.ui.clientes

import Cliente
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.app.dao.SQLiteHelper
import com.dam.tp2dam.R
import com.dam.tp2dam.app.ui.panel.control.PanelControlActivity

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

    private fun setupClickListeners() {
        findViewById<android.widget.ImageView>(R.id.btnBack).setOnClickListener {
            startActivity(Intent(this, PanelControlActivity::class.java));
        }
    }

    private fun configurarRecyclerView() {
        adapter = ClientesAdapter(
            listaClientes,
            helper
        )

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
        val cardClientes = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardClientes)

        cardClientes.setOnClickListener {
            val vista = layoutInflater.inflate(R.layout.dialog_cliente, null)
            val etNombre = vista.findViewById<EditText>(R.id.etNombre)
            val etDni = vista.findViewById<EditText>(R.id.etDni)

            val dialog = AlertDialog.Builder(this)
                .setTitle("Nuevo Socio")
                .setView(vista)
                .setPositiveButton("Guardar") { _, _ ->

                    // TODO MODIFICAR JUAN PABLO. REEMPLAZAR vista POR LAYOUT DE REGIOSTRO DE SOCIOS (REGISTRO DE CLIENTES) o hacerl navegable, a conveniencia del desarrollador
                    val nombreToString = etNombre.text.toString()
                    val dniToString = etDni.text.toString()

                    val cliente = Cliente(
                        dniToString,
                        nombreToString,
                        "",
                        "",
                        "",
                        true,
                        true,
                        obbtenerFechaActual()
                    )

                    val clienteDao = helper.getClienteDao()
                    clienteDao.insertar(cliente)

                    adapter.cargarClientes()

                    Toast.makeText(
                        this,
                        "Cliente agregado",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()

            dialog.show()
        }
    }

    private fun filtrarClientes(texto: String) {
        val clienteDao = helper.getClienteDao()
        val clientesDB = clienteDao.obtenerHabilitados()

        listaClientes.clear()

        if (texto.isEmpty()) {
            listaClientes.addAll(clientesDB)
        } else {
            listaClientes.addAll(
                clientesDB.filter {
                    it.dni.contains(texto)
                }
            )
        }

        adapter.notifyDataSetChanged()
    }

    private fun obbtenerFechaActual(): String {
        val fechaActual = java.util.Calendar.getInstance()
        val dia = fechaActual.get(java.util.Calendar.DAY_OF_MONTH)
        val mes = fechaActual.get(java.util.Calendar.MONTH) + 1
        val anio = fechaActual.get(java.util.Calendar.YEAR)
        return String.format(java.util.Locale.US, "%04d-%02d-%02d", anio, mes, dia)
    }

}
