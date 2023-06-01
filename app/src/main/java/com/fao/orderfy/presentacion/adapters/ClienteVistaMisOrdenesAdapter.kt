package com.fao.orderfy.presentacion.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.ClienteVistaMisOrdenesListener

class ClienteVistaMisOrdenesAdapter(
    private val listaOrdenes: List<Orden>,
    private val listaTienda: List<Tienda>,
    private val listaProducto: List<Producto>,
    val listener: ClienteVistaMisOrdenesListener
) : RecyclerView.Adapter<ClienteVistaMisOrdenesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.list_misordenes, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orden = listaOrdenes[position]
        val tienda = listaTienda[position]
        val producto = listaProducto[position]
        var minutes = orden.horaPedido.minutes.toString().toInt()
        var hours = orden.horaPedido.hours.toString().toInt()

        if (hours >= 0 && hours < 10 && minutes > 9) {
            hours = "0$hours".toInt()
        } else if (hours >= 0 && hours < 10 && minutes < 10) {
            hours = "0$hours".toInt()
            minutes = "0$minutes".toInt()
        } else if (hours > 9 && minutes < 10) {
            minutes = "0$minutes".toInt()
        }

        var tiempoEstimado = minutes + producto.tiempoEstimado

        if (tiempoEstimado >= 60) {
            hours += 1
            tiempoEstimado -=60
        }

        if (hours == 24 && tiempoEstimado >= 60) {
            hours = 1
            tiempoEstimado -=60
        }

        holder.bind(orden, tienda, tiempoEstimado, minutes, hours)



        holder.constrain.setOnClickListener {
            listener.onSelectItemClick(orden, producto, tienda)
        }
    }

    override fun getItemCount(): Int {
        return listaOrdenes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEstadoOrden: TextView = itemView.findViewById(R.id.tvEnPreparacion)
        private val tvNombreLocal: TextView = itemView.findViewById(R.id.tvTitulo)
        private val tvCodigo: TextView = itemView.findViewById(R.id.tvCodigo)
        private val tvTiempoEstimado: TextView = itemView.findViewById(R.id.tvtiempoEstimado)
        private val ivImagenEstado: ImageView = itemView.findViewById(R.id.ivEtapaPreparacion)
        var constrain = itemView.findViewById<ConstraintLayout>(R.id.constraint)

        fun bind(orden: Orden, tienda: Tienda, tiempoEstimado: Int, minutes: Int, hours: Int) {

            tvEstadoOrden.text = orden.estado.toString()
            tvCodigo.text = orden.idOrden.toString()
            tvNombreLocal.text = tienda.nombre
            val formattedHours = hours.toString().padStart(2, '0')
            val formattedTiempoEstimado = tiempoEstimado.toString().padStart(2, '0')
            tvTiempoEstimado.text = "$formattedHours:$formattedTiempoEstimado"
            if (orden.estado == 2) {
                ivImagenEstado.setImageResource(R.drawable.bolsa)
                tvEstadoOrden.setTextColor(Color.parseColor("#219549"))
                tvEstadoOrden.text = "Entregado"
            } else if (orden.estado == 1) {
                ivImagenEstado.setImageResource(R.drawable.cocinando)
                tvEstadoOrden.setTextColor(Color.parseColor("#1434DF"))
                tvEstadoOrden.text = "Listo"
            }else{
                ivImagenEstado.setImageResource(R.drawable.cocinando)
                tvEstadoOrden.setTextColor(Color.parseColor("#99991246"))
                tvEstadoOrden.text = "En preparacion"
            }
        }
    }
}