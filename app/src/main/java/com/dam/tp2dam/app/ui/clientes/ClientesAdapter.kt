package com.dam.tp2dam.app.ui.clientes

import Cliente
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.R
import com.dam.tp2dam.app.dao.SQLiteHelper

class ClientesAdapter(
    private val lista: MutableList<Cliente>,
    private val helper: SQLiteHelper

): RecyclerView.Adapter<ClientesAdapter.ViewHolder>() {

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        val tvDetalleCliente = vista.findViewById<TextView>(R.id.tvDetalleCliente)
        val tvFechaAlta = vista.findViewById<TextView>(R.id.tvFechaAlta)
        val btnCarnet = vista.findViewById<ImageButton>(R.id.btnCarnet)
        val btnCobrar = vista.findViewById<ImageButton>(R.id.btnCobrar)
        val btnEditar = vista.findViewById<ImageButton>(R.id.btnEditar)
        val btnEliminar = vista.findViewById<ImageButton>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)

        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = lista[position]

        holder.tvDetalleCliente.text = "${cliente.dni} - ${cliente.nombre}"
        holder.tvFechaAlta.text = formatearFecha(cliente.fechaAlta)

        holder.btnEliminar.setOnClickListener {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Eliminar Cliente")
                    .setMessage("¿Eliminar ${cliente.nombre}?")
                    .setPositiveButton("Eliminar") { _, _ ->

                        val clienteDao = helper.getClienteDao()

                        clienteDao.eliminar(cliente.dni)
                        cargarClientes()

                        Toast.makeText(
                            holder.itemView.context,
                            "Cliente eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
        }

        holder.btnCarnet.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "TODO imprimir carnet de ${cliente.nombre} (DNI: ${cliente.dni})",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.btnCobrar.setOnClickListener {
            Toast.makeText(
                // TODO MODIFICAR JAVIER
                holder.itemView.context,
                "TODO pagar para ${cliente.nombre} (DNI: ${cliente.dni})",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.btnEditar.setOnClickListener {
            // TODO MODIFICAR JUAN PABLO
            Toast.makeText(
                holder.itemView.context,
                "TODO MODIFICAR ${cliente.nombre} (DNI: ${cliente.dni})",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun cargarClientes() {
        val clienteDao = helper.getClienteDao()
        val clientesDB = clienteDao.obtenerHabilitados()

        lista.clear()
        lista.addAll(clientesDB)
        notifyDataSetChanged()
    }

    private fun formatearFecha(fecha: String): String {
        val partes = fecha.split("-")
        return if (partes.size == 3) {
            "${partes[2]}/${partes[1]}/${partes[0]}"
        } else {
            fecha
        }
    }



}
