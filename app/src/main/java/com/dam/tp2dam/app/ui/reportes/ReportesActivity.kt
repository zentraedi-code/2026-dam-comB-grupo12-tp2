package com.dam.tp2dam.app.ui.reportes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.SQLiteHelper
import com.dam.tp2dam.app.ui.panel.control.PanelControlActivity

class ReportesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reportes)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val txtMensaje = findViewById<LinearLayout>(R.id.layoutEmptyState)
        val recyclerView = findViewById<RecyclerView>(R.id.rvSociosVencidos)

        recyclerView.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener {
            startActivity(Intent(this, PanelControlActivity::class.java))
        }

        val lista = SQLiteHelper(this)
            .getFacturaDao()
            .obtenerSociosVencidos()

        if (lista.isEmpty()) {
            txtMensaje.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            txtMensaje.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            recyclerView.adapter = ReportesAdapter(lista)
        }
    }
}