package com.dam.tp2dam.app.ui.administrador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.R

class AdministradoresAdapter(
    private val lista: MutableList<Administrador>,
    private val onEditar: (Administrador) -> Unit,
    private val onEliminar: (Administrador) -> Unit
) : RecyclerView.Adapter<AdministradoresAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreAdmin)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstadoAdmin)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaAdmin)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditarAdmin)
        val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminarAdmin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_administrador, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val admin = lista[position]
        holder.tvNombre.text = "#${admin.id}  ${admin.nombre}"
        holder.tvEstado.text = admin.estado

        val color = if (admin.estado == "Activo")
            holder.itemView.context.getColor(R.color.green_700)
        else
            holder.itemView.context.getColor(android.R.color.darker_gray)

        holder.tvEstado.setTextColor(color)
        holder.tvFecha.text = admin.fechaAlta
        holder.btnEditar.setOnClickListener { onEditar(admin) }
        holder.btnEliminar.setOnClickListener { onEliminar(admin) }
    }

    override fun getItemCount() = lista.size
}
