package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentPerfilBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.sql.Time
import java.text.SimpleDateFormat

class PerfilFragment : Fragment() {
    private lateinit var fbinding: FragmentPerfilBinding
    private lateinit var sesionCliente: Cliente
    var listaOrden: MutableList<Orden> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentPerfilBinding.inflate(layoutInflater)
        fbinding.btnEditarPerfil.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_perfilFragment_to_editarPerfilFragment)
        }
        sesionCliente = ClienteActivity.sesionClienteGlobal!!
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.tvNombre.text = sesionCliente.nombre
        fbinding.tvNombreUsuario.text = sesionCliente.nombreUsuario
        cargarDatosOrdenes()
    }
    private fun cargarDatosOrdenes() {
        val activity = activity
        if (isAdded && activity != null) {
            listaOrden.clear()
            val sListaTienda: MutableList<Tienda> = mutableListOf()
            val sListaProducto: MutableList<Producto> = mutableListOf()
            val viewModelOrden = ViewModelProvider(requireActivity())[ViewModelOrden::class.java]
            viewModelOrden.consultarOrdenCliente(requireContext(), object : MainListener {
                override fun onSuccess(response: JsonArray) {
                    if (response != null) {
                        val jsonArrayString = response.toString()
                        val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray

                        listaOrden.addAll(jsonArray.map { element ->
                            val jsonObject = element.asJsonObject
                            val id = jsonObject.get("id").asString.toInt()
                            val idProducto = jsonObject.get("idProducto").asString.toInt()
                            val idCliente = jsonObject.get("idCliente").asString.toInt()
                            val horaPedido =
                                convertStringToTime(jsonObject.get("horaPedido").asString)!!
                            val cantidadElement = jsonObject.get("cantidad")
                            val cantidad =
                                if (cantidadElement.isJsonNull) 1 else cantidadElement.asString.toInt()
                            val horaReclamo =
                                convertStringToTime(jsonObject.get("horaReclamo").asString)!!
                            val estado = jsonObject.get("estado").asString.toInt()

                            val orden =
                                Orden(
                                    id,
                                    horaPedido,
                                    horaReclamo,
                                    cantidad,
                                    estado,
                                    idCliente,
                                    idProducto
                                )

                            orden
                        })

                        fbinding.tvPedidosRealizados.text = listaOrden.size.toString()

                    } else {
                        Toast.makeText(activity, "No hay Ordenes", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(error: String) {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                    }

                }
            }, sesionCliente)
        }
    }
    fun convertStringToTime(timeString: String): Time? {
        val format = SimpleDateFormat("HH:mm:ss")
        return try {
            val date = format.parse(timeString)
            Time(date.time)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}