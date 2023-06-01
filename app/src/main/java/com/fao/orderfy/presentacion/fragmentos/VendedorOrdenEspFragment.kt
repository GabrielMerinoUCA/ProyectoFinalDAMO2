package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentVendedorOrdenEspBinding
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.google.gson.JsonArray
import java.sql.Time
import java.time.LocalTime


class VendedorOrdenEspFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorOrdenEspBinding
    private lateinit var viewModelOrden: ViewModelOrden
    private lateinit var viewModelProducto: ViewModelProducto
    private lateinit var orden: Orden
    private lateinit var producto: Producto
    private var sesionVendedor = VendedorActivity.sesionVendedorGlobal

    private val args: VendedorOrdenEspFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorOrdenEspBinding.inflate(layoutInflater)
        viewModelOrden = ViewModelProvider(this)[ViewModelOrden::class.java]
        viewModelProducto = ViewModelProvider(this)[ViewModelProducto::class.java]
        orden = args.orden
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        cargarProducto()

    }

    private fun cargarProducto() {
        viewModelProducto.consultarProducto(requireActivity(), object : MainListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(response: JsonArray) {
                if (validarJsonArray(response)) {
                    obtenerProducto(response)
                    cargarVistas()
                    Log.wtf("VendedorMenuFragment", "Cargado productos")
                }
            }

            override fun onFailure(error: String) {
                Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
            }

        }, sesionVendedor!!.idTienda)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun cargarVistas() {
        with(fbinding) {
            tvTiempoPreparacion.text = orden.horaPedido.toString()
            tvCodigo.text = "#${orden.idOrden}"

            val bitmap: Bitmap? =
                BitmapFactory.decodeByteArray(producto.imagen, 0, producto.imagen.size)
            Glide.with(requireContext())
                .load(bitmap)
                .transform(
                    CenterCrop(),
                    RoundedCorners(20),
                )
                .into(ivProd)

            tvCantidad.text = "x${orden.cantidad}"
            tvNombreProd.text = producto.nombre
            tvPrecioProd.text = "C\$ ${producto.precio}"
            tvSubTotal.text = "C\$ ${producto.precio * orden.cantidad}"
            tvTotal.text = "C\$ ${(producto.precio * orden.cantidad) + 10}"

            //estado 0 es pendiente, estado 1 es lista, estodo 3 es reclamada
            if (orden.estado == 0) {
                btnOrdenLista.text = "Orden Lista"
                btnOrdenLista.setOnClickListener {
                    viewModelOrden.cambiarEstadoOrdenLista(object : MainListener {
                        override fun onSuccess(response: JsonArray) {
                            if (validarJsonArray(response)) {
                                Toast.makeText(
                                    requireContext(),
                                    "Se a marcado lista la orden",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Navigation.findNavController(fbinding.root)
                                    .navigate(R.id.action_vendedorOrdenEspFragment_to_vendedorHomeFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "No se pudo realizar el cambio",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(error: String) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                        }

                    }, orden)
                }
            } else if (orden.estado == 1) {
                btnOrdenLista.text = "Orden Terminada"
                btnOrdenLista.setOnClickListener {
                    val tiempoActual = LocalTime.now()
                    orden.horaReclamo = parseLocalTimeToSqlTime(tiempoActual)
                    viewModelOrden.cambiarEstadoOrdenReclamada(object : MainListener {
                        override fun onSuccess(response: JsonArray) {
                            if (validarJsonArray(response)) {
                                Toast.makeText(
                                    requireContext(),
                                    "La orden fue reclamada",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Navigation.findNavController(fbinding.root)
                                    .navigate(R.id.action_vendedorOrdenEspFragment_to_vendedorHomeFragment)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "No se pudo realizar el cambio",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(error: String) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                        }

                    }, orden)
                }
            } else if (orden.estado == 2) {
                btnOrdenLista.visibility = View.GONE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseLocalTimeToSqlTime(localTime: LocalTime): Time {
        val hour = localTime.hour
        val minute = localTime.minute
        val second = localTime.second

        return Time(hour, minute, second)
    }

    private fun obtenerProducto(response: JsonArray) {
        val length = response.size()
        var i = 0
        do {
            if (length != 0) {
                val productoJson = response.get(i).asJsonObject
                val id = productoJson.get("id").asInt

                if (id == orden.idProducto) {
                    val nombre = productoJson.get("nombre").asString
                    val precio = productoJson.get("precio").asFloat

                    val logoBase64 = productoJson.get("imagen").asString
                    val logoByteArray = Base64.decode(logoBase64, Base64.DEFAULT)

                    val idTienda = productoJson.get("idTienda").asInt

                    val tiempoEstimado = productoJson.get("tiempoEstimado").asInt

                    producto = Producto(
                        id,
                        nombre,
                        precio,
                        logoByteArray,
                        "",
                        true,
                        tiempoEstimado,
                        idTienda
                    )

                }

            }
            i++
        } while (i < length)
    }

    private fun validarJsonArray(jsonArray: JsonArray): Boolean {
        if (jsonArray.size() > 0) {
            val jsonObject = jsonArray.get(0).asJsonObject
            if (!jsonObject.has("response")) return true
            val valor: Boolean? = jsonObject.get("response").asBoolean
            if (valor != null) {
                return valor
            }
        }
        return false
    }

}