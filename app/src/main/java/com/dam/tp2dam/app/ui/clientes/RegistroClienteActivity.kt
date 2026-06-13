package com.dam.tp2dam.app.ui.clientes

import Cliente
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.SQLiteHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val CLAVE_DNI = "dni"

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etDni: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etEmail: EditText
    private lateinit var switchTipoCliente: SwitchCompat
    private lateinit var switchAptoFisico: SwitchCompat
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelar: Button
    private lateinit var helper: SQLiteHelper

    private var clienteEdicion: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        helper = SQLiteHelper(this)

        inicializarViews()
        configurarSwitches()
        cargarModoEdicion()
        configurarBotones()
    }

    private fun cargarModoEdicion() {
        val dni = intent.getStringExtra(CLAVE_DNI) ?: return
        val cliente = helper.getClienteDao().buscarPorDni(dni) ?: return
        clienteEdicion = cliente

        findViewById<TextView>(R.id.txtTitulo)?.text = "Editar Cliente"
        btnGuardar.text = "Actualizar"

        etNombre.setText(cliente.nombre)
        etApellido.setText(cliente.apellido)
        etDni.setText(cliente.dni)
        etDni.isEnabled = false
        etTelefono.setText(cliente.telefono)
        etEmail.setText(cliente.email)

        switchTipoCliente.isChecked = cliente.esSocio
        switchAptoFisico.isChecked = cliente.aptoFisico == true
    }

    private fun inicializarViews() {
        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etDni = findViewById(R.id.etDni)
        etTelefono = findViewById(R.id.etTelefono)
        etEmail = findViewById(R.id.etEmail)
        switchTipoCliente = findViewById(R.id.switchTipoCliente)
        switchAptoFisico = findViewById(R.id.switchAptoFisico)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
    }

    private fun configurarSwitches() {
        switchTipoCliente.isChecked = false
        switchAptoFisico.isEnabled = false
        switchAptoFisico.isChecked = false
        switchAptoFisico.alpha = 0.4f

        switchTipoCliente.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchAptoFisico.isEnabled = true
                switchAptoFisico.alpha = 1f
            } else {
                switchAptoFisico.isChecked = false
                switchAptoFisico.isEnabled = false
                switchAptoFisico.alpha = 0.4f
            }
        }
    }

    private fun configurarBotones() {
        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val dni = etDni.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val clienteDao = helper.getClienteDao()
            val editando = clienteEdicion != null

            if (!editando && clienteDao.buscarPorDni(dni) != null) {
                Toast.makeText(this, "Ya existe un cliente con ese DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val esSocio = switchTipoCliente.isChecked
            val tipoCliente = if (esSocio) "SOCIO" else "NO_SOCIO"
            val aptoFisico: Boolean? = if (esSocio) switchAptoFisico.isChecked else null
            val fechaAlta = clienteEdicion?.fechaAlta
                ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val habilitado = clienteEdicion?.habilitado ?: true

            val cliente = Cliente(
                dni = dni,
                nombre = nombre,
                apellido = apellido,
                telefono = telefono,
                email = email,
                habilitado = habilitado,
                esSocio = esSocio,
                fechaAlta = fechaAlta,
                tipoCliente = tipoCliente,
                aptoFisico = aptoFisico
            )

            if (editando) {
                val filas = clienteDao.actualizar(cliente)
                if (filas > 0) {
                    Toast.makeText(this, "Cliente actualizado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar el cliente", Toast.LENGTH_SHORT).show()
                }
            } else {
                val id = clienteDao.insertar(cliente)
                if (id != -1L) {
                    Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar el cliente", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }
}
