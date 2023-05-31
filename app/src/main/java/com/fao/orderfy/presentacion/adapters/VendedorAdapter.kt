package com.fao.orderfy.presentacion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.utils.VendedorListener


class VendedorAdapter(
    var  vendedorList: ArrayList<Vendedor>,
    val listener: VendedorListener
): RecyclerView.Adapter<VendedorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.vendedor_list, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vendedor = vendedorList[position]
        holder.tvNombre.text = vendedor.nombreUsuario
        holder.button.setOnClickListener {listener.onDeleteVendedor(vendedor)  }
    }

    override fun getItemCount(): Int {
        return vendedorList.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        var tvNombre = itemView.findViewById<TextView>(R.id.tvNombreVendedor)
        var button = itemView.findViewById<Button>(R.id.btnEliminar)
    }
}