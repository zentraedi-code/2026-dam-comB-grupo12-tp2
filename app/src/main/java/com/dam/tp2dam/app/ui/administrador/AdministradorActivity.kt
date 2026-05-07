package com.dam.tp2dam.app.ui.administrador

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dam.tp2dam.R
import com.dam.tp2dam.databinding.ActivityAdministradorBinding

data class Administrador(
    val id: Int,
    val nombre: String,
    val estado: String,
    val fechaAlta: String
)

class AdministradorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdministradorBinding
    private lateinit var adapter: AdministradoresAdapter

    private val listaCompleta = mutableListOf(
        Administrador(1, "Admin", "Activo", "01/04/2026")
    )
    private var listaFiltrada = listaCompleta.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdministradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarToolbar()
        configurarRecyclerView()
        configurarBuscador()
        configurarFab()
        actualizarTotal()
    }

    private fun configurarToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun configurarRecyclerView() {
        adapter = AdministradoresAdapter(
            lista = listaFiltrada,
            onEditar = { admin -> mostrarDialogoEditar(admin) },
            onEliminar = { admin -> mostrarDialogoEliminar(admin) }
        )
        binding.rvAdministradores.layoutManager = LinearLayoutManager(this)
        binding.rvAdministradores.adapter = adapter
    }

    private fun configurarBuscador() {
        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { filtrarLista(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filtrarLista(query: String) {
        listaFiltrada.clear()
        listaFiltrada.addAll(
            if (query.isEmpty()) listaCompleta
            else listaCompleta.filter { it.nombre.contains(query, ignoreCase = true) }
        )
        adapter.notifyDataSetChanged()
        actualizarTotal()
    }

    private fun configurarFab() {
        binding.fabAgregar.setOnClickListener { mostrarDialogoAgregar() }
    }

    private fun actualizarTotal() {
        binding.tvTotal.text = "Total: ${listaFiltrada.size} administradores"
    }

    private fun mostrarDialogoAgregar() {
        val etNombre = EditText(this).apply { hint = "Nombre del administrador" }
        AlertDialog.Builder(this)
            .setTitle("Nuevo Administrador")
            .setView(etNombre)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = etNombre.text.toString().trim()
                if (nombre.isNotEmpty()) {
                    val nuevoId = (listaCompleta.maxOfOrNull { it.id } ?: 0) + 1
                    listaCompleta.add(Administrador(nuevoId, nombre, "Activo", obtenerFechaHoy()))
                    filtrarLista(binding.etBuscar.text.toString())
                    Toast.makeText(this, "Administrador creado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoEditar(admin: Administrador) {
        val etNombre = EditText(this).apply { setText(admin.nombre) }
        AlertDialog.Builder(this)
            .setTitle("Editar Administrador")
            .setView(etNombre)
            .setPositiveButton("Guardar") { _, _ ->
                val nombreNuevo = etNombre.text.toString().trim()
                if (nombreNuevo.isNotEmpty()) {
                    val index = listaCompleta.indexOfFirst { it.id == admin.id }
                    if (index != -1) {
                        listaCompleta[index] = admin.copy(nombre = nombreNuevo)
                        filtrarLista(binding.etBuscar.text.toString())
                        Toast.makeText(this, "Administrador actualizado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoEliminar(admin: Administrador) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Administrador")
            .setMessage("¿Confirmás que querés eliminar a ${admin.nombre}?")
            .setPositiveButton("Eliminar") { _, _ ->
                listaCompleta.removeIf { it.id == admin.id }
                filtrarLista(binding.etBuscar.text.toString())
                Toast.makeText(this, "Administrador eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun obtenerFechaHoy(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}
