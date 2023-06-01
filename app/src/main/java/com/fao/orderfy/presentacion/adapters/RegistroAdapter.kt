package com.fao.orderfy.presentacion.adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.utils.RegistroListener


class RegistroAdapter(
    var registroList: ArrayList<Registro>,
    val registroListener: RegistroListener,
) : RecyclerView.Adapter<RegistroAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.solicitudes, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registroList[position]
        val nombre = registro.nombre + " " + registro.apellido
        holder.tvSolicitante.text = nombre
        if(registro.estado == 0){
            holder.tvEstadoSolicitud.text = "Pendiente"
        }
        if(registro.estado == 1){
            holder.tvEstadoSolicitud.text = "Aceptado"
            holder.tvEstadoSolicitud.setTextColor(Color.parseColor("#219549"))
            holder.ivEstadoSolicitud.setImageResource(R.drawable.aceptar)
        }
        if(registro.estado == 2){
            holder.tvEstadoSolicitud.text = "Rechazado"
            holder.tvEstadoSolicitud.setTextColor(Color.parseColor("#C13030"))
            holder.ivEstadoSolicitud.setImageResource(R.drawable.rechazado)
        }

        holder.tvNombreLocal.text = registro.nombreTienda
        holder.btnRegistro.setOnClickListener {
            registroListener.onSelectedItem(registro)
        }

    }

    override fun getItemCount(): Int {
        return registroList.size
    }

    inner class ViewHolder(val ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var tvSolicitante = itemView.findViewById<TextView>(R.id.tvSolicitante)
        var tvEstadoSolicitud = itemView.findViewById<TextView>(R.id.tvEstadoSolicitud)
        var tvNombreLocal = itemView.findViewById<TextView>(R.id.tvNombreLocal)
        var ivEstadoSolicitud = itemView.findViewById<ImageView>(R.id.ivEstadoSolicitud)
        var btnRegistro = itemView.findViewById<ConstraintLayout>(R.id.rvClickable)

    }
}

