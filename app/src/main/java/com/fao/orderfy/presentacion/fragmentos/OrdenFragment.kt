package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.databinding.FragmentOrdenBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.google.gson.JsonArray
import java.sql.Time
import java.time.LocalTime


class OrdenFragment : Fragment() {
    private lateinit var fbinding: FragmentOrdenBinding
    private val args by navArgs<OrdenFragmentArgs>()
    private lateinit var sesionCliente: Cliente

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentOrdenBinding.inflate(layoutInflater)
        sesionCliente = ClienteActivity.sesionClienteGlobal!!
        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        val (producto, tienda, cantidad) = cargarDatos()
        fbinding.tvNombreProd.text = producto.nombre
        val bitmap: Bitmap? =
            BitmapFactory.decodeByteArray(producto.imagen, 0, producto.imagen.size)
        Glide.with(requireContext())
            .load(bitmap)
            .transform(
                CenterCrop(),
                RoundedCorners(20),
            )
            .into(fbinding.ivProd)
        fbinding.tvCantidadProd.text = cantidad.toString()
        fbinding.tvPrecioProd.text = producto.precio.toString()
        val (subTotal, tarifaServicio, total) =calcularTotal(cantidad, producto.precio)
        fbinding.btnRealizarPedido.setOnClickListener {
            realizarPedido(subTotal, tarifaServicio, total)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun realizarPedido(subTotal: Float, tarifaServicio: Int, total: Float) {
        val (producto, tienda, cantidad) = cargarDatos()
        val time = LocalTime.now()
        val tiempo = Time(time.hour, time.minute, time.second)
        var orden = Orden(0, tiempo, tiempo, cantidad, 1, sesionCliente.idCliente, producto.idProducto)
        val viewModelOrden =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelOrden::class.java]
        viewModelOrden.insertarOrden(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val action = OrdenFragmentDirections.actionOrdenFragmentToOrdenRealizadaFragment(producto, tienda, orden,  subTotal, tarifaServicio, total)
                findNavController().navigate(action)

            }

            override fun onFailure(error: String) {

                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, orden)


}

private fun cargarDatos(): Triple<Producto, Tienda, Int> {
    val producto = args.producto
    val tienda = args.tienda
    val cantidad = args.cantidad
    return Triple(producto, tienda, cantidad)
}

private fun calcularTotal(cantidad: Int, precio: Float): Triple<Float, Int, Float> {
    var subTotal = precio * cantidad
    var tarifaServicio = 10
    var total: Float = subTotal + tarifaServicio
    fbinding.tvSubTotal.text = subTotal.toString()
    fbinding.tvTarifaServicio.text = tarifaServicio.toString()
    fbinding.tvTotal.text = total.toString()
    return Triple(subTotal, tarifaServicio, total)

}
}