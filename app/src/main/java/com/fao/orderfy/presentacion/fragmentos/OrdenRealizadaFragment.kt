package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentOrdenRealizadaBinding
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda

class OrdenRealizadaFragment : Fragment() {
    private val args by navArgs<OrdenRealizadaFragmentArgs>()
    private lateinit var fbinding: FragmentOrdenRealizadaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentOrdenRealizadaBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        val (producto, tienda, orden) = cargarDatos()
        val (subTotal, tarifaServicio, total) =  cargarDatosPrecio()
        with(fbinding){
            tvLocal.text = tienda.nombre
            tvPrecioTotal.text = total.toString()
            tvSubTotal.text = subTotal.toString()
            tvTarifaServicio.text = tarifaServicio.toString()
            var minutes = orden.horaPedido.minutes.toString().toInt()
            var hours = orden.horaPedido.hours.toString().toInt()

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

            tvHoraEstimada.text = "$formattedHours:$formattedTiempoEstimado"
            btnContinuar.setOnClickListener {
                val navController = findNavController()


                val homeFragmentId = navController.graph.startDestinationId


                if (navController.currentDestination?.id != homeFragmentId) {

                    navController.popBackStack(homeFragmentId, false)
                }
            }

        }
    }

    private fun cargarDatos(): Triple<Producto, Tienda, Orden> {
        val producto = args.producto
        val tienda = args.tienda
        val orden = args.orden
        return Triple(producto, tienda, orden)

    }

    private fun cargarDatosPrecio(): Triple<Float, Int, Float> {
        val subTotal = args.subTotal
        val tarifaServicio = args.tarifaServicio
        val total = args.total
        return Triple(subTotal, tarifaServicio, total)
    }
}