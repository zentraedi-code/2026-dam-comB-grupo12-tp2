package com.dam.tp2dam.app.ui.carnets

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dam.tp2dam.databinding.ActivityCarnetsBinding

data class Carnet(
    val numero: Int,
    val nombreSocio: String,
    val dni: String,
    val estadoPago: String,
    var estadoCarnet: String
)

class CarnetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarnetsBinding
    private lateinit var adapter: CarnetsAdapter

    private val listaCarnets = mutableListOf(
        Carnet(1, "Carlos Vargas",     "32456199", "Completado", "Pendiente de entrega"),
        Carnet(2, "Sandra Gomez",      "33456159", "Completado", "Entregado"),
        Carnet(3, "Margot Robbie",     "31466159", "Completado", "Entregado"),
        Carnet(4, "Scarlett Johansson","28456199", "Completado", "Pendiente de entrega"),
        Carnet(5, "Pedro Martínez",    "27891234", "Completado", "Pendiente de entrega"),
        Carnet(6, "Ana Rodríguez",     "30123456", "Completado", "Entregado")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarnetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarToolbar()
        configurarRecyclerView()
        actualizarContador()
    }

    private fun configurarToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun configurarRecyclerView() {
        adapter = CarnetsAdapter(
            lista = listaCarnets,
            onGenerarPdf = { carnet -> generarPdf(carnet) },
            onMarcarEntregado = { carnet -> marcarComoEntregado(carnet) }
        )
        binding.rvCarnets.layoutManager = LinearLayoutManager(this)
        binding.rvCarnets.adapter = adapter
    }

    private fun actualizarContador() {
        binding.tvTotalSocios.text = "${listaCarnets.size} socios con pago completado"
    }

    private fun generarPdf(carnet: Carnet) {
        // Acá iría la lógica real de generación de PDF con el backend
        Toast.makeText(this, "Generando PDF para ${carnet.nombreSocio}...", Toast.LENGTH_SHORT).show()
    }

    private fun marcarComoEntregado(carnet: Carnet) {
        if (carnet.estadoCarnet == "Entregado") {
            Toast.makeText(this, "Este carnet ya fue entregado", Toast.LENGTH_SHORT).show()
            return
        }
        val index = listaCarnets.indexOfFirst { it.numero == carnet.numero }
        if (index != -1) {
            listaCarnets[index] = carnet.copy(estadoCarnet = "Entregado")
            adapter.notifyItemChanged(index)
            Toast.makeText(this, "Carnet de ${carnet.nombreSocio} marcado como entregado", Toast.LENGTH_SHORT).show()
        }
    }
}
