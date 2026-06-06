package com.dam.tp2dam.app.ui.administrador

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dam.tp2dam.R
import com.dam.tp2dam.databinding.ActivityAdministradorBinding

data class Administrador(
    val id: Int,
    val nombre: String,
    val usuario: String,
    val clave: String,
    val estado: String,
    val fechaAlta: String
)

class AdministradorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdministradorBinding
    private lateinit var adapter: AdministradoresAdapter

    private val listaCompleta = mutableListOf<Administrador>(
        Administrador(1, "Admin", "Admin","123456", "Activo", obbtenerFechaActual())
    )
    private var listaFiltrada = listaCompleta.toMutableList<Administrador>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdministradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecyclerView()
        configurarBuscador()
        configurarAgregar()
        actualizarTotal()
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

    private fun configurarAgregar() {
        binding.btnAgregar.setOnClickListener { mostrarDialogoAgregar() }
    }

    private fun actualizarTotal() {
        binding.tvTotal.text = "Total: ${listaFiltrada.size} administradores"
    }

    private fun mostrarDialogoAgregar() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_form_administrador, null)
        
        val tilNombre = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilNombre)
        val tilUsuario = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilUsuario)
        val tilClave = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilClave)
        
        val etNombre = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etNombre)
        val etUsuario = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etUsuario)
        val etClave = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etClave)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Nuevo Administrador")
            .setView(dialogView)
            .setPositiveButton("Guardar", null)
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val nombre = etNombre.text.toString().trim()
                val usuario = etUsuario.text.toString().trim()
                val clave = etClave.text.toString().trim()
                
                var isValid = true
                
                if (nombre.isEmpty()) {
                    tilNombre.error = "El nombre es obligatorio"
                    isValid = false
                } else {
                    tilNombre.error = null
                }
                
                if (usuario.isEmpty()) {
                    tilUsuario.error = "El usuario es obligatorio"
                    isValid = false
                } else {
                    tilUsuario.error = null
                }
                
                if (clave.isEmpty()) {
                    tilClave.error = "La clave es obligatoria"
                    isValid = false
                } else {
                    tilClave.error = null
                }
                
                if (isValid) {
                    val nuevoId = (listaCompleta.maxOfOrNull { it.id } ?: 0) + 1
                    listaCompleta.add(Administrador(nuevoId, nombre, usuario, clave, "Activo", obbtenerFechaActual()))
                    filtrarLista(binding.etBuscar.text.toString())
                    Toast.makeText(this, "Administrador creado", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }
        
        dialog.show()
    }

    private fun mostrarDialogoEditar(admin: Administrador) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_form_administrador, null)

        val tilNombre = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilNombre)
        val tilUsuario = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilUsuario)
        val tilClave = dialogView.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilClave)

        val etNombre = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etNombre)
        val etUsuario = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etUsuario)
        val etClave = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etClave)

        // Pre-llenar los campos con los datos existentes
        etNombre.setText(admin.nombre)
        etUsuario.setText(admin.usuario)
        etClave.setText(admin.clave)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Editar Administrador")
            .setView(dialogView)
            .setPositiveButton("Guardar", null)
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val nombre = etNombre.text.toString().trim()
                val usuario = etUsuario.text.toString().trim()
                val clave = etClave.text.toString().trim()

                var isValid = true

                if (nombre.isEmpty()) {
                    tilNombre.error = "El nombre es obligatorio"
                    isValid = false
                } else {
                    tilNombre.error = null
                }

                if (usuario.isEmpty()) {
                    tilUsuario.error = "El usuario es obligatorio"
                    isValid = false
                } else {
                    tilUsuario.error = null
                }

                if (clave.isEmpty()) {
                    tilClave.error = "La clave es obligatoria"
                    isValid = false
                } else {
                    tilClave.error = null
                }

                if (isValid) {
                    val index = listaCompleta.indexOfFirst { it.id == admin.id }
                    if (index != -1) {
                        listaCompleta[index] = admin.copy(
                            nombre = nombre,
                            usuario = usuario,
                            clave = clave
                        )
                        filtrarLista(binding.etBuscar.text.toString())
                        Toast.makeText(this, "Administrador actualizado", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            }
        }

        dialog.show()
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

    private fun obbtenerFechaActual(): String {
        val fechaActual = java.util.Calendar.getInstance()
        val dia = fechaActual.get(java.util.Calendar.DAY_OF_MONTH)
        val mes = fechaActual.get(java.util.Calendar.MONTH) + 1
        val anio = fechaActual.get(java.util.Calendar.YEAR)
        return "$dia/$mes/$anio"
    }

}
