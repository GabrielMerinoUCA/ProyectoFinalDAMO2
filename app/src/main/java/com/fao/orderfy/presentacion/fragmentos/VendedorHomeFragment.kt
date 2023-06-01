package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.databinding.FragmentVendedorHomeBinding
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.adapters.OrdenListaListAdapter
import com.fao.orderfy.presentacion.adapters.OrdenesNuevosListAdapter
import com.fao.orderfy.presentacion.adapters.VendedorProdListAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.google.gson.JsonArray
import java.sql.Time
import java.text.SimpleDateFormat


class VendedorHomeFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorHomeBinding
    private lateinit var sesionVendedor: Vendedor
    private lateinit var viewModelOrden: ViewModelOrden
    private lateinit var ordenPendiente: ArrayList<Orden>
    private lateinit var ordenLista: ArrayList<Orden>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorHomeBinding.inflate(layoutInflater)
        sesionVendedor = VendedorActivity.sesionVendedorGlobal!!
        viewModelOrden = ViewModelProvider(this)[ViewModelOrden::class.java]
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        ordenPendiente = ArrayList()
        ordenLista = ArrayList()
        obtenerOrdenesPendientes()
        obtenerOrdenesListas()
    }
    private fun obtenerOrdenesListas() {
        viewModelOrden.consultarOrdenTiendaLista(object : MainListener{
            override fun onSuccess(response: JsonArray) {
                if(validarJsonArray(response)){
                    cargarListaOrdenesListas(response)
                    cargarRecyclerOrdenesListas()
                } else {
                    Log.wtf("VendedorMenuFragment", "JSON NO FUE VALIDADO!")
                }
            }

            override fun onFailure(error: String) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }, sesionVendedor.idTienda)
    }

    private fun cargarRecyclerOrdenesListas() {
        fbinding.rvOrdenesListas.layoutManager = LinearLayoutManager(context)
        fbinding.rvOrdenesListas.adapter = OrdenListaListAdapter(ordenLista,this)
        fbinding.rvOrdenesListas.setHasFixedSize(true)
        if(ordenLista.size > 0){
            fbinding.tvAvisoOrdenesListas.visibility = View.GONE
        }
        else{
            fbinding.tvAvisoOrdenesListas.visibility = View.VISIBLE
        }
    }

    private fun cargarListaOrdenesListas(response: JsonArray) {
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
                ordenLista.add(Orden(
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


    private fun obtenerOrdenesPendientes() {
        viewModelOrden.consultarOrdenTiendaPendiente(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                if (validarJsonArray(response)) {
                    cargarListaOrdenesPendientes(response)
                    cargarRecyclerOrdenesPendientes()
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

    private fun cargarRecyclerOrdenesPendientes() {
        fbinding.rvOrdenesNuevas.layoutManager = LinearLayoutManager(context)
        fbinding.rvOrdenesNuevas.adapter = OrdenesNuevosListAdapter(ordenPendiente,this)
        fbinding.rvOrdenesNuevas.setHasFixedSize(true)
        if(ordenPendiente.size > 0){
            fbinding.tvAvisoOrdenesNuevas.visibility = View.GONE
        }
        else{
            fbinding.tvAvisoOrdenesNuevas.visibility = View.VISIBLE
        }
    }

    private fun cargarListaOrdenesPendientes(response: JsonArray) {
        val length = response.size()
        var i = 0
        do {
            if (length != 0) {
                val ordenPendienteJson = response.get(i).asJsonObject
                val id = ordenPendienteJson.get("id").asInt
                val idProducto = ordenPendienteJson.get("idProducto").asInt
                val idCliente = ordenPendienteJson.get("idCliente").asInt
                val horaPedido = convertStringToTime(ordenPendienteJson.get("horaPedido").asString)!!
                val cantidad = ordenPendienteJson.get("cantidad").asInt
                val horaReclamo = convertStringToTime(ordenPendienteJson.get("horaReclamo").asString)!!
                val estado = ordenPendienteJson.get("estado").asInt
                ordenPendiente.add(Orden(
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