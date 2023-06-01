package com.fao.orderfy.presentacion.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.fao.orderfy.datos.utils.ClienteVistaTiendasListener

class ClienteVistaTiendaAdapter (
    val listaTienda: MutableList<Tienda>,
    val context: Context,
    val listener: ClienteVistaTiendasListener
): RecyclerView.Adapter<ClienteVistaTiendaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.local_list, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tienda= listaTienda[position]

        holder.tvNombre.text = tienda.nombre

        /*val buffer = producto.imagen.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val myBitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        val resources = Resources.getSystem()
        val myDrawable: Drawable = BitmapDrawable(resources,  myBitmap)*/
        val bitmap: Bitmap? = BitmapFactory.decodeByteArray(tienda.logo, 0, tienda.logo.size)
        //holder.tvImagen.setImageBitmap(bitmap)

        Glide.with(context)
            .load(bitmap)
            .transform(
                CenterCrop(),
                RoundedCorners(20),
            )
            .into(holder.tvImagen)


        /*holder.constrain.setOnClickListener {
            listener.onEditItemClick(estudiante)
        }

        holder.btnEliminar.setOnClickListener {
            listener.onDeleteItemClick(estudiante)
        }*/

        holder.constrain.setOnClickListener {
            listener.onSelectItemClick(tienda)
        }

    }

    override fun getItemCount(): Int {
        return listaTienda.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

        var tvNombre = itemView.findViewById<TextView>(R.id.tvNombreProdList)
        var tvImagen = itemView.findViewById<ImageView>(R.id.ivLogoTienda)
        var constrain = itemView.findViewById<ConstraintLayout>(R.id.constraintTienda)

    }
}