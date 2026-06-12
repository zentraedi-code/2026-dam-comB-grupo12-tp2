package com.dam.tp2dam.app.ui.panel.control

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dam.tp2dam.MainActivity
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.ClienteDao
import com.dam.tp2dam.app.dao.FacturaDao
import com.dam.tp2dam.app.dao.SQLiteHelper
import com.dam.tp2dam.app.ui.reportes.ReportesActivity
import com.dam.tp2dam.app.ui.clientes.ClienteActivity

class PanelControlActivity : AppCompatActivity() {
    private lateinit var txtCantidadSocios: TextView
    private lateinit var txtCantidadDeuda: TextView
    private lateinit var txtCantidadAlDia: TextView
    private lateinit var clienteDao: ClienteDao
    private lateinit var facturaDao: FacturaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_panel_control)

        val sqliteHelper = SQLiteHelper(this)

        clienteDao = sqliteHelper.getClienteDao()
        facturaDao = sqliteHelper.getFacturaDao()

        setupClickListeners()
        cerrarSesion()

        txtCantidadSocios = findViewById(R.id.txtCantidadSocios)
        txtCantidadDeuda = findViewById(R.id.txtCantidadDeuda)
        txtCantidadAlDia = findViewById(R.id.txtCantidadAlDia)

        actualizarResumen()
    }
    private fun setupClickListeners() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnClientes).setOnClickListener {
            startActivity(Intent(this, ClienteActivity::class.java));
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnReportes).setOnClickListener {
            startActivity(Intent(this, ReportesActivity::class.java));
        }
    }

    private fun actualizarResumen() {
        val totalSocios = clienteDao.obtenerCantidadSocios()
        val sociosConDeuda = facturaDao.obtenerCantidadSociosVencidos()
        val sociosAlDia = clienteDao.obtenerCantidadNoSocios()

        txtCantidadSocios.text = totalSocios.toString()
        txtCantidadDeuda.text = sociosConDeuda.toString()
        txtCantidadAlDia.text = sociosAlDia.toString()
    }

    private fun cerrarSesion(){
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Querés cerrar sesión?")
                .setPositiveButton("Sí") { _, _ ->

                    val intent = Intent(this, MainActivity::class.java)

                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}