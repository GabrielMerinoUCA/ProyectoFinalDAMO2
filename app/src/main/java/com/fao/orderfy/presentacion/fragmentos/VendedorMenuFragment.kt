package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentVendedorMenuBinding
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.adapters.VendedorProdListAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.google.gson.JsonArray


class VendedorMenuFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorMenuBinding
    private lateinit var viewModelProducto: ViewModelProducto
    private lateinit var sesionVendedor: Vendedor
    private lateinit var productoList: ArrayList<Producto>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorMenuBinding.inflate(layoutInflater)
        viewModelProducto = ViewModelProvider(this)[ViewModelProducto::class.java]
        sesionVendedor = VendedorActivity.sesionVendedorGlobal!!
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        obtenerProductos()
        fbinding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(fbinding.root)
                .navigate(R.id.action_vendedorMenuFragment_to_vendedorAgregarProdFragment)
        }
    }

    private fun obtenerProductos() {
        viewModelProducto.consultarProducto(requireActivity(), object : MainListener {
            override fun onSuccess(response: JsonArray) {
                if(validarJsonArray(response)){
                    productoList = ArrayList()
                    cargarListaProducto(response)
                    cargarRecycler()
                    Log.wtf("VendedorMenuFragment", "Cargado productos")
                }
                else{
                    Log.wtf("JSON", "JSON NO FUE VALIDADO!")
                }
            }

            override fun onFailure(error: String) {
                Log.wtf("VendedorMenuFragment", error)
            }
        }, sesionVendedor.idTienda)
    }

    private fun cargarListaProducto(response: JsonArray) {
        val length = response.size()
        var i = 0
        do {
            if (length != 0) {
                val productoJson = response.get(i).asJsonObject
                val id = productoJson.get("id").asInt
                val nombre = productoJson.get("nombre").asString
                val precio = productoJson.get("precio").asFloat
                val descripcion = productoJson.get("descripcion").asString

                val idTienda = sesionVendedor.idTienda

                val disponibilidad = productoJson.get("disponibilidad").asInt
                var disponibilidadBool = false

                if(disponibilidad == 1) {
                    disponibilidadBool = true
                }

                val tiempoEstimado = productoJson.get("tiempoEstimado").asInt

                productoList.add(
                    Producto(
                        id,
                        nombre,
                        precio,
                        ByteArray(0),
                        descripcion,
                        disponibilidadBool,
                        tiempoEstimado,
                        idTienda
                    )
                )
            }
            i++
        } while (i < length)
    }

    private fun cargarRecycler() {
        fbinding.rvProductosMenu.layoutManager = LinearLayoutManager(context)
        fbinding.rvProductosMenu.adapter = VendedorProdListAdapter(productoList)
        fbinding.rvProductosMenu.setHasFixedSize(true)
        Log.wtf("ListaMenu", "${productoList.size}" )
    }
    private fun validarJsonArray(jsonArray: JsonArray): Boolean {
        if (jsonArray.size() > 0) {
            val jsonObject = jsonArray.get(0).asJsonObject
            if(!jsonObject.has("response")) return true
            val valor: Boolean? = jsonObject.get("response").asBoolean
            if(valor != null) {
                return valor
            }
        }
        return false
    }
    override fun onResume() {
        super.onResume()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event or simply ignore it
                Toast.makeText(requireContext(), "Use el sidebar para navegar", Toast.LENGTH_SHORT).show()
            }
        })
    }
}