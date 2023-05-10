package com.fao.orderfy.presentacion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.R

class ProdAdapter (
    val listaProd: MutableList<Producto>,
    //val listener: AdaptadorListener
): RecyclerView.Adapter<ProdAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.tienda_prod, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = listaProd[position]

        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = producto.precio.toString()
        holder.tvImagen.setImageDrawable(producto.imagen)


        /*holder.constrain.setOnClickListener {
            listener.onEditItemClick(estudiante)
        }

        holder.btnEliminar.setOnClickListener {
            listener.onDeleteItemClick(estudiante)
        }*/

    }

    override fun getItemCount(): Int {
        return listaProd.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

        var tvNombre = itemView.findViewById<TextView>(R.id.tvNombreProd)
        var tvPrecio = itemView.findViewById<TextView>(R.id.tvPrecioProd)
        var tvImagen = itemView.findViewById<ImageView>(R.id.ivProd)

    }
}