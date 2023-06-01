package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentOrdenEspecificaBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.io.ByteArrayInputStream

class OrdenEspecificaFragment : Fragment() {
    private lateinit var fbinding: FragmentOrdenEspecificaBinding
    private val args by navArgs<OrdenEspecificaFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentOrdenEspecificaBinding.inflate(layoutInflater)
        iniciar()

        return fbinding.root
    }

    private fun iniciar() {
        cargarDatosEnET()
        cargarDatosProducto()
        calcularTotal(
            fbinding.tvCantidadProd.text.toString().toInt(),
            fbinding.tvPrecioProd.text.toString().toFloat()
        )
        var cantidadProd = (fbinding.tvCantidadProd.text.toString().toInt())
        var precio = (fbinding.tvPrecioProd.text.toString().toFloat())

        fbinding.btnVolverPedir.setOnClickListener {

            volverAPedir()
        }

    }

    fun cargarAtras(){
        val navController = findNavController()
        navController.popBackStack()
    }

    fun volverAPedir() {

        val (orden, producto, tienda) = obtenerArg()
        if (orden != null && producto != null && tienda != null) {
            val viewModelOrden =
                ViewModelProvider(context as ViewModelStoreOwner)[ViewModelOrden::class.java]
            viewModelOrden.insertarOrden(object : MainListener {
                override fun onSuccess(response: JsonArray) {

                    Toast.makeText(requireContext(), "Orden Insertada Correctamente", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(error: String) {

                    activity?.runOnUiThread {
                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                    }
                }

            }, orden)

        } else {

            Toast.makeText(requireContext(), "No hay datos", Toast.LENGTH_LONG).show()
        }


    }

    private fun obtenerArg(): Triple<Orden?, Producto?, Tienda?> {
        val orden = args.orden
        val producto = args.producto
        val tienda = args.tienda
        return Triple(orden, producto, tienda)
    }

    private fun cargarDatosEnET() {
        val (orden, producto, tienda) = obtenerArg()

        if (orden != null && producto != null && tienda != null) {
            fbinding.tvCodigo.text = orden.idOrden.toString()
            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(tienda.logo, 0, tienda.logo.size)
            Glide.with(requireContext())
                .load(bitmap)
                .transform(
                    CenterCrop(),
                    RoundedCorners(20),
                )
                .into(fbinding.ivCazuela2)
            var minutes = orden.horaPedido.minutes.toString().toInt()
            var hours = orden.horaPedido.hours.toString().toInt()
            if (orden.estado == 2) {
                fbinding.tvEnPreparacion.text = "En Preparacion"
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

                val formattedHours = hours.toString().padStart(2, '0')
                val formattedTiempoEstimado = tiempoEstimado.toString().padStart(2, '0')

                fbinding.tvTiempoPreparacion.text = "$formattedHours:$formattedTiempoEstimado"
            } else if (orden.estado == 1) {
                fbinding.ivCazuela // Reemplaza "binding" con el nombre de tu objeto de enlace
                    .let { Glide.with(this).load(R.drawable.cocinando).into(it) }
                fbinding.tvEnPreparacion.setTextColor(Color.parseColor("#1434DF"))
                fbinding.tvEnPreparacion.text = "Listo"
                fbinding.btnVolverPedir.isVisible = false
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

                val formattedHours = hours.toString().padStart(2, '0')
                val formattedTiempoEstimado = tiempoEstimado.toString().padStart(2, '0')

                fbinding.tvTiempoPreparacion.text = "$formattedHours:$formattedTiempoEstimado"
            }else {
                fbinding.ivCazuela // Reemplaza "binding" con el nombre de tu objeto de enlace
                    .let { Glide.with(this).load(R.drawable.cocinando).into(it) }
                fbinding.tvEnPreparacion.setTextColor(getResources().getColor(R.color.rojo))
                fbinding.tvEnPreparacion.text = "En Preparación"
                fbinding.btnVolverPedir.isVisible = false
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

                val formattedHours = hours.toString().padStart(2, '0')
                val formattedTiempoEstimado = tiempoEstimado.toString().padStart(2, '0')

                fbinding.tvTiempoPreparacion.text = "$formattedHours:$formattedTiempoEstimado"
            }
        } else {
            Toast.makeText(requireContext(), "NAAAAAAAAAAAAAAAA", Toast.LENGTH_LONG).show()
        }

    }

    private fun calcularTotal(cantidad: Int, precio: Float) {
        var subTotal = precio * cantidad
        var tarifaServicio = 10
        var total: Float = subTotal + tarifaServicio
        fbinding.tvSubTotal.text = subTotal.toString()
        fbinding.tvTarifaServicio.text = tarifaServicio.toString()
        fbinding.tvTotal.text = total.toString()

    }

    private fun cargarDatosProducto() {
        val (orden, producto, tienda) = obtenerArg()
        if (orden != null && producto != null && tienda != null) {
            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(producto.imagen, 0, producto.imagen.size)
            Glide.with(requireContext())
                .load(bitmap)
                .transform(
                    CenterCrop(),
                    RoundedCorners(20),
                )
                .into(fbinding.ivProd)
            fbinding.tvNombreProd.text = producto.nombre
            fbinding.tvPrecioProd.text = producto.precio.toString()
            fbinding.tvCantidadProd.text = orden.cantidad.toString()

        } else {
            Toast.makeText(requireContext(), "NAAAAAAAAAAAAAAAA", Toast.LENGTH_LONG).show()
        }

    }

}