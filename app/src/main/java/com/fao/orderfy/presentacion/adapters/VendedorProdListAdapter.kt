package com.fao.orderfy.presentacion.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.presentacion.fragmentos.VendedorMenuFragmentDirections

class VendedorProdListAdapter(var productosList: List<Producto>) :
    RecyclerView.Adapter<VendedorProdListAdapter.VendedorProdListHolder>() {

    inner class VendedorProdListHolder(val origin: View, val context: Context) :
        RecyclerView.ViewHolder(origin) {
        private val estadoProd = origin.findViewById<TextView>(R.id.tvEstadoProd)
        private val nombreProd = origin.findViewById<TextView>(R.id.tvNombreProd)
        private val productoVendedor =
            origin.findViewById<ConstraintLayout>(R.id.clProductoVendedor)

        fun load(producto: Producto) {
            if (producto.disponibilidad) {
                estadoProd.text = "Disponible"
                val color = ContextCompat.getColor(context, R.color.verde)
                estadoProd.setTextColor(color)
            } else {
                estadoProd.text = "Agotado"
                val color = ContextCompat.getColor(context, R.color.rojo)
                estadoProd.setTextColor(color)
            }
            nombreProd.text = producto.nombre
            productoVendedor.setOnClickListener {
                val action = VendedorMenuFragmentDirections.actionVendedorMenuFragmentToVendedorEditarProdFragment(producto)
                Navigation.findNavController(origin).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendedorProdListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VendedorProdListHolder(
            layoutInflater.inflate(
                R.layout.vendedor_prod_list,
                parent,
                false
            ), parent.context
        )
    }

    override fun getItemCount() = productosList.size

    override fun onBindViewHolder(holder: VendedorProdListHolder, position: Int) {
        val producto = productosList[position]
        holder.load(producto)
    }
}