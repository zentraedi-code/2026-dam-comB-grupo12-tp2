package com.dam.tp2dam.app.ui.panel.control

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dam.tp2dam.app.ui.cuotas.CuotasActivity
import com.dam.tp2dam.R
import com.dam.tp2dam.app.ui.administrador.AdministradorActivity
import com.dam.tp2dam.app.ui.carnets.CarnetsActivity
import com.dam.tp2dam.app.ui.reportes.ReportesActivity

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

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnAdministrador).setOnClickListener {
            // Toast.makeText(this, "En construcción", Toast.LENGTH_SHORT).show();
            startActivity(Intent(this, AdministradorActivity::class.java));
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCarnet).setOnClickListener {
            startActivity(Intent(this, CarnetsActivity::class.java));
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCuotas).setOnClickListener {
            startActivity(Intent(this, CuotasActivity::class.java));
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnReportes).setOnClickListener {
            // Toast.makeText(this, "En construcción", Toast.LENGTH_SHORT).show();
            startActivity(Intent(this, ReportesActivity::class.java));
        }
    }
}