package com.dam.tp2dam.app.ui.carnets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.dam.tp2dam.R

class CarnetsAdapter(
    private val lista: MutableList<Carnet>,
    private val onGenerarPdf: (Carnet) -> Unit,
    private val onMarcarEntregado: (Carnet) -> Unit
) : RecyclerView.Adapter<CarnetsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFoto: ImageView = itemView.findViewById(R.id.ivFotoSocio)
        val tvNumero: TextView = itemView.findViewById(R.id.tvNumeroCarne)
        val tvBadge: TextView = itemView.findViewById(R.id.tvBadgeEstado)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreSocio)
        val tvDni: TextView = itemView.findViewById(R.id.tvDniSocio)
        val tvEstadoPago: TextView = itemView.findViewById(R.id.tvEstadoPago)
        val btnGenerarPdf: MaterialButton = itemView.findViewById(R.id.btnGenerarPdf)
        val btnEntregado: MaterialButton = itemView.findViewById(R.id.btnEntregado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carnet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carnet = lista[position]

        holder.tvNumero.text = "Nº ${carnet.numero}"
        holder.tvNombre.text = carnet.nombreSocio
        holder.tvDni.text = "DNI: ${carnet.dni}"
        holder.tvEstadoPago.text = carnet.estadoPago
        holder.tvBadge.text = carnet.estadoCarnet

        // Color del badge según estado
        val badgeColor = if (carnet.estadoCarnet == "Pendiente de entrega")
            holder.itemView.context.getColor(R.color.green_500)
        else
            holder.itemView.context.getColor(R.color.green_700)
        holder.tvBadge.setBackgroundColor(badgeColor)

        // Botón Entregado: deshabilitado si ya fue entregado
        if (carnet.estadoCarnet == "Entregado") {
            holder.btnEntregado.isEnabled = false
            holder.btnEntregado.alpha = 0.5f
        } else {
            holder.btnEntregado.isEnabled = true
            holder.btnEntregado.alpha = 1.0f
        }

        holder.btnGenerarPdf.setOnClickListener { onGenerarPdf(carnet) }
        holder.btnEntregado.setOnClickListener { onMarcarEntregado(carnet) }
    }

    override fun getItemCount() = lista.size
}
