package com.dam.tp2dam.app.ui.panel.control

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dam.tp2dam.R
import com.dam.tp2dam.app.ui.reportes.ReportesActivity
import com.dam.tp2dam.app.ui.clientes.ClienteActivity

class PanelControlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_panel_control)
        setupClickListeners()
    }
    private fun setupClickListeners() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancelar)
            .setOnClickListener {
                finish();
            }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnClientes).setOnClickListener {
            // Toast.makeText(this, "En construcción", Toast.LENGTH_SHORT).show();
            startActivity(Intent(this, ClienteActivity::class.java));
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnReportes).setOnClickListener {
            // Toast.makeText(this, "En construcción", Toast.LENGTH_SHORT).show();
            startActivity(Intent(this, ReportesActivity::class.java));
        }
    }
}