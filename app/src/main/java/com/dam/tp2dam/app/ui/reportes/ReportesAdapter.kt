package com.dam.tp2dam.app.ui.reportes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dam.tp2dam.R
import com.dam.tp2dam.app.domain.SocioVencido
import java.text.SimpleDateFormat
import java.util.Locale

class ReportesAdapter(
    private val lista: List<SocioVencido>
) : RecyclerView.Adapter<ReportesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtDni: TextView = itemView.findViewById(R.id.txtDni)

        val txtTelefono: TextView = itemView.findViewById(R.id.txtTelefono)
        val txtImporte: TextView = itemView.findViewById(R.id.txtImporte)
        val txtFechaVencimiento: TextView = itemView.findViewById(R.id.txtFechaVencimiento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_socio_vencido, parent, false)

        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val socio = lista[position]

        holder.txtNombre.text = "${socio.nombre} ${socio.apellido}"
        holder.txtDni.text = socio.dni
        holder.txtTelefono.text = socio.telefono
        holder.txtImporte.text = "$${socio.importe}"

        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.txtFechaVencimiento.text = formato.format(socio.fechaVencimiento)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}