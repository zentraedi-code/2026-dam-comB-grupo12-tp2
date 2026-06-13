package com.dam.tp2dam.app.ui.clientes

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.dam.tp2dam.R

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        inicializarViews()
        configurarSwitches()
        configurarBotones()
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
        // Estado inicial: NO_SOCIO
        switchTipoCliente.isChecked = false
        switchAptoFisico.isEnabled = false
        switchAptoFisico.isChecked = false
        switchAptoFisico.alpha = 0.4f

        switchTipoCliente.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // SOCIO
                switchAptoFisico.isEnabled = true
                switchAptoFisico.alpha = 1f
            } else {
                // NO SOCIO
                switchAptoFisico.isChecked = false
                switchAptoFisico.isEnabled = false
                switchAptoFisico.alpha = 0.4f
            }
        }
    }

    private fun configurarBotones() {
        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val dni = etDni.text.toString()

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
                Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipoSocio = if (switchTipoCliente.isChecked) "SOCIO" else "NO_SOCIO"
            val aptoFisico = if (switchAptoFisico.isChecked) 1 else 0

            Toast.makeText(
                this,
                "Cliente registrado:\n$nombre $apellido\nTipo: $tipoSocio\nApto físico: $aptoFisico",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }
}
