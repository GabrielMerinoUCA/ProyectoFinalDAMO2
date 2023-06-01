package com.fao.orderfy.presentacion.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.ClienteVistaProdLocalListener

class ClienteVistaProdLocalAdapter (
    val listaProd: MutableList<Producto>,
    val context: Context,
    val listener: ClienteVistaProdLocalListener
): RecyclerView.Adapter<ClienteVistaProdLocalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.tienda_prod, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto= listaProd[position]

        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = producto.precio.toString()

        /*val buffer = producto.imagen.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val myBitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val resources = Resources.getSystem()
        val myDrawable: Drawable = BitmapDrawable(resources,  myBitmap)*/
        val bitmap: Bitmap? = BitmapFactory.decodeByteArray(producto.imagen, 0, producto.imagen.size)
        //holder.tvImagen.setImageBitmap(bitmap)

        Glide.with(context)
            .load(bitmap)
            .transform(
                CenterCrop(),
                RoundedCorners(20),
            )
            .into(holder.tvImagen)

        holder.constrain.setOnClickListener {
            listener.onSelectItemClick(producto)
        }

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
        var constrain = itemView.findViewById<ConstraintLayout>(R.id.constraintProd)

    }
}