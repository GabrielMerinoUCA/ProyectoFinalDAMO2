package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.databinding.FragmentVendedorOrdenesBinding
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.adapters.ListMisordenesAdapter
import com.fao.orderfy.presentacion.adapters.OrdenesNuevosListAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.google.gson.JsonArray
import java.sql.Time
import java.text.SimpleDateFormat


class VendedorOrdenesFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorOrdenesBinding
    private lateinit var sesionVendedor: Vendedor
    private lateinit var viewModelOrden: ViewModelOrden
    private lateinit var ordenReclamada: ArrayList<Orden>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorOrdenesBinding.inflate(layoutInflater)
        sesionVendedor = VendedorActivity.sesionVendedorGlobal!!
        viewModelOrden = ViewModelProvider(this)[ViewModelOrden::class.java]
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        ordenReclamada = ArrayList()
        obtenerOrdenesReclamadas()
    }

    private fun obtenerOrdenesReclamadas() {
        viewModelOrden.consultarOrdenTiendaEntregada(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                if (validarJsonArray(response)) {
                    cargarListaOrdenesReclamadas(response)
                    cargarRecyclerOrdenesReclamadas()
                    Log.wtf("VendedorMenuFragment", "Cargado productos")
                } else {
                    Log.wtf("JSON", "JSON NO FUE VALIDADO!")
                }
            }

            override fun onFailure(error: String) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }

        }, sesionVendedor.idTienda)
    }

    private fun cargarRecyclerOrdenesReclamadas() {
        fbinding.rvMisOrdenes.layoutManager = LinearLayoutManager(context)
        fbinding.rvMisOrdenes.adapter = ListMisordenesAdapter(ordenReclamada,this)
        fbinding.rvMisOrdenes.setHasFixedSize(true)
    }

    private fun convertStringToTime(timeString: String): Time? {
        val format = SimpleDateFormat("HH:mm:ss")
        return try {
            val date = format.parse(timeString)
            Time(date.time)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun cargarListaOrdenesReclamadas(response: JsonArray) {
        val length = response.size()
        var i = 0
        do {
            if (length != 0) {
                val ordenListaJson = response.get(i).asJsonObject
                val id = ordenListaJson.get("id").asInt
                val idProducto = ordenListaJson.get("idProducto").asInt
                val idCliente = ordenListaJson.get("idCliente").asInt
                val horaPedido = convertStringToTime(ordenListaJson.get("horaPedido").asString)!!
                val cantidad = ordenListaJson.get("cantidad").asInt
                val horaReclamo = convertStringToTime(ordenListaJson.get("horaReclamo").asString)!!
                val estado = ordenListaJson.get("estado").asInt
                ordenReclamada.add(Orden(
                    id,
                    horaPedido,
                    horaReclamo,
                    cantidad,
                    estado,
                    idCliente,
                    idProducto
                ))
            }
            i++
        } while (i < length)
    }

    private fun validarJsonArray(response: JsonArray): Boolean {
        if (response.size() > 0) {
            val jsonObject = response.get(0).asJsonObject
            if (!jsonObject.has("response")) return true
            val valor: Boolean? = jsonObject.get("response").asBoolean
            if (valor != null) {
                return valor
            }
        }
        return false
    }


}