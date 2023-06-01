package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentPlatilloBinding
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda

class PlatilloFragment : Fragment() {
    private lateinit var fbinding: FragmentPlatilloBinding
    private val args by navArgs<PlatilloFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentPlatilloBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {

        val datos = recuperarDatos()
        val tienda = datos.first
        val producto = datos.second
        fbinding.tvTituloProd.text = producto.nombre
        fbinding.tvDescText.text = producto.descripcion
        fbinding.tvPrecio.text = producto.precio.toString()
        fbinding.tvTiempo.text = producto.tiempoEstimado.toString()

        val bitmap: Bitmap? = BitmapFactory.decodeByteArray(producto.imagen, 0, producto.imagen.size)
        Glide.with(requireContext())
            .load(bitmap)
            .transform(
                CenterCrop(),
                RoundedCorners(20),
            )
            .into(fbinding.ivImagenProd)
        var cantidadProd = 1
        var precio = fbinding.tvPrecio.text.toString().toFloat()
        calcularSubTotal(cantidadProd, precio)

        fbinding.btnDisminuirCantidad.setOnClickListener {

            if (cantidadProd > 1) {
                cantidadProd--
                calcularSubTotal(cantidadProd, precio)

            }
        }

        fbinding.btnAumentarCantidad.setOnClickListener {
            cantidadProd++
            calcularSubTotal(cantidadProd, precio)
        }
        fbinding.btnAddOrder.setOnClickListener {
            val action = PlatilloFragmentDirections.actionPlatilloFragmentToOrdenFragment(producto, tienda, cantidadProd)
            findNavController().navigate(action)
        }

    }

    private fun recuperarDatos(): Pair<Tienda, Producto> {
        val producto = args.producto
        val tienda = args.tienda

        return Pair(tienda, producto)
    }
    private fun calcularSubTotal(cantidad: Int, precio: Float) {
        var subTotal = precio * cantidad
        fbinding.tvCantidadProd.text = cantidad.toString()
        fbinding.tvPrecio.text= subTotal.toString()

    }

}