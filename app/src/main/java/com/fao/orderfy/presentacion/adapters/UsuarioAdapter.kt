package com.fao.orderfy.presentacion.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.utils.ClienteListener

class UsuarioAdapter(
    var  clienteList: ArrayList<Cliente>,
    val listener: ClienteListener
): RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.usuario_list, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clienteList[position]
        holder.tvNombre.text = cliente.nombreUsuario
        holder.button.setOnClickListener {listener.onDeleteItem(cliente)  }
    }

    override fun getItemCount(): Int {
        return clienteList.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        var tvNombre = itemView.findViewById<TextView>(R.id.tvNombreUsuario)
        var button = itemView.findViewById<Button>(R.id.btnEliminar)

    }
}