package com.dam.tp2dam.app.ui.panel.control

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
}