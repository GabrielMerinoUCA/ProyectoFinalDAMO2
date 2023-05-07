package com.fao.orderfy.Recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.Entidades.Producto
import com.fao.orderfy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PopularProdAdapter (
    val listaProd: MutableList<Producto>,
    //val listener: AdaptadorListener
): RecyclerView.Adapter<PopularProdAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.popular_prod_list, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = listaProd[position]

        holder.tvNombre.text = producto.nombre
        holder.tvDesc.text = producto.descripcion
        holder.tvImagen = producto.imagen


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

        var tvNombre = itemView.findViewById<TextView>(R.id.tvProd)
        var tvDesc = itemView.findViewById<TextView>(R.id.tvDescProd)
        var tvImagen = itemView.findViewById<ImageView>(R.id.ivPopularProd)

    }
}