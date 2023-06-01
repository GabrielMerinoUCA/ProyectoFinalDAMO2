package com.fao.orderfy.presentacion.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.fragmentos.VendedorHomeFragmentDirections
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.google.gson.JsonArray

class OrdenListaListAdapter(
    val ordenListaList: List<Orden>,
    val viewModelStoreOwner: ViewModelStoreOwner
) : RecyclerView.Adapter<OrdenListaListAdapter.OrdenListaListHolder>() {
    private var viewModelCliente = ViewModelProvider(viewModelStoreOwner)[ViewModelCliente::class.java]
    private val cliente = ArrayList<Cliente>()
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


    inner class OrdenListaListHolder(val origin: View) : RecyclerView.ViewHolder(origin) {
        private val tvNombreCliente = origin.findViewById<TextView>(R.id.tvNombreCliente)
        private val tvCodigo = origin.findViewById<TextView>(R.id.tvCodigo)
        private val tvTiempoEstimado = origin.findViewById<TextView>(R.id.tvtiempoEstimado)
        private val rvOrdenLista = origin.findViewById<ConstraintLayout>(R.id.constraintOrdenLista)

        fun load(orden: Orden) {
            tvCodigo.text = "#${orden.idOrden}"
            for (x in cliente) {
                if (x.idCliente == orden.idCliente) {
                    tvNombreCliente.text = "${x.nombre}"
                }
            }
            tvTiempoEstimado.text = orden.horaPedido.toString()
            rvOrdenLista.setOnClickListener {
                val action = VendedorHomeFragmentDirections.actionVendedorHomeFragmentToVendedorOrdenEspFragment(orden)
                Navigation.findNavController(origin).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenListaListHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.orden_listas_list, parent, false)
        return OrdenListaListHolder(vista)
    }

    override fun getItemCount() = ordenListaList.size

    override fun onBindViewHolder(holder: OrdenListaListHolder, position: Int) {
        val orden = ordenListaList[position]
        holder.load(orden)
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
}