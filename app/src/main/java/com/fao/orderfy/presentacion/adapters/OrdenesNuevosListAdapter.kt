package com.fao.orderfy.presentacion.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.fragmentos.VendedorHomeFragmentDirections
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.google.gson.JsonArray

class OrdenesNuevosListAdapter(
    val ordenesPendiente: List<Orden>,
    val viewModelStoreOwner: ViewModelStoreOwner
) :
    RecyclerView.Adapter<OrdenesNuevosListAdapter.OrdenesNuevosListHolder>() {
    private val cliente: ArrayList<Cliente> = ArrayList()
    private var viewModelCliente: ViewModelCliente =
        ViewModelProvider(viewModelStoreOwner)[ViewModelCliente::class.java]

    init {
        viewModelCliente.consultarCliente(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                if (validarJsonArray(response)) {
                    cargarCliente(response)
                    Log.wtf("VendedorMenuFragment", "Cargado productos")
                } else {
                    Log.wtf("JSON", "JSON NO FUE VALIDADO!")
                }
            }

            override fun onFailure(error: String) {
                Log.wtf("OrdenesNuevosListAdapter", error)
            }

        })
    }

    private fun cargarCliente(response: JsonArray) {
        val length = response.size()
        var i = 0
        do {
            if (length != 0) {
                val clienteJson = response.get(i).asJsonObject
                val id = clienteJson.get("id").asInt
                val nombre = clienteJson.get("nombre").asString

                cliente.add(
                    Cliente(
                        id,
                        nombre,
                        "",
                        "",
                        ""
                    )
                )
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

    inner class OrdenesNuevosListHolder(val origin: View) : RecyclerView.ViewHolder(origin) {
        val tvNombreCliente = origin.findViewById<TextView>(R.id.tvNombreCliente)
        val tvCodigo = origin.findViewById<TextView>(R.id.tvCodigo)
        val tvTiempoEstimado = origin.findViewById<TextView>(R.id.tvtiempoEstimado)
        val rvOrdenPendiente = origin.findViewById<ConstraintLayout>(R.id.constraintOrdenPendiente)

        fun load(orden: Orden) {
            tvCodigo.text = "#${orden.idOrden}"
            for (x in cliente) {
                if (x.idCliente == orden.idCliente) {
                    tvNombreCliente.text = "${x.nombre}"
                }
            }
            tvTiempoEstimado.text = orden.horaPedido.toString()
            rvOrdenPendiente.setOnClickListener {
                val action = VendedorHomeFragmentDirections.actionVendedorHomeFragmentToVendedorOrdenEspFragment(orden)
                Navigation.findNavController(origin).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenesNuevosListHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.ordenes_nuevas_list, parent, false)
        return OrdenesNuevosListHolder(vista)
    }

    override fun getItemCount() = ordenesPendiente.size

    override fun onBindViewHolder(holder: OrdenesNuevosListHolder, position: Int) {
        val ordenPendiente = ordenesPendiente[position]
        holder.load(ordenPendiente)
    }
}