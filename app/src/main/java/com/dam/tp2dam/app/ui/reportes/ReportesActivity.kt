package com.dam.tp2dam.app.ui.reportes

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dam.tp2dam.R
import com.dam.tp2dam.app.ui.panel.control.PanelControlActivity

class ReportesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reportes)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, PanelControlActivity::class.java)
            startActivity(intent)
        }

        val opciones = listOf("Cuotas que vencen hoy", "Socios con deuda", "Socios al día", "Todos los socios")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opciones)

        val autoComplete = findViewById<AutoCompleteTextView>(R.id.autoCompleteReportes)
        autoComplete.setAdapter(adapter)
    }
}