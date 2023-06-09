package com.fao.orderfy.presentacion.fragmentos

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentMisOrdenesBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Orden
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.ClienteVistaMisOrdenesListener
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.adapters.ClienteVistaMisOrdenesAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelOrden
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.fao.orderfy.presentacion.viewmodel.ViewModelTienda
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.sql.Time
import java.text.SimpleDateFormat
import android.os.Process
import android.util.Log
import androidx.navigation.fragment.findNavController


class MisOrdenesFragment : Fragment(), ClienteVistaMisOrdenesListener {
    private lateinit var fbinding: FragmentMisOrdenesBinding
    var listaOrden: MutableList<Orden> = mutableListOf()
    lateinit var listaTienda: MutableList<Tienda>
    lateinit var listaProducto: MutableList<Producto>
    var sListaTienda: MutableList<Tienda> = mutableListOf()
    var sListaProducto: MutableList<Producto> = mutableListOf()
    var listaID: MutableList<Int> = mutableListOf()
    var tienda: Tienda? = null
    var listaIDTienda: MutableList<Int> = mutableListOf()
    var producto: Producto? = null

    lateinit var adatadorMisOrdenes: ClienteVistaMisOrdenesAdapter
    private lateinit var sesionCliente: Cliente
    override fun onResume() {
        super.onResume()
        listaOrden.clear()
        listaProducto.clear()
        listaTienda.clear()
        sListaProducto.clear()
        sListaTienda.clear()
        listaID.clear()
        //iniciar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentMisOrdenesBinding.inflate(layoutInflater)
        sesionCliente = ClienteActivity.sesionClienteGlobal!!
        listaTienda = mutableListOf()
        listaProducto = mutableListOf()


        iniciar()
        return fbinding.root
    }



    private fun iniciar() {
        cargarDatosTienda()

    }


    private fun cargarDatosOrdenes(listaTienda: List<Tienda>, listaProd: List<Producto>) {
        val activity = activity
        if (isAdded && activity != null) {
            val viewModelOrden = ViewModelProvider(requireActivity())[ViewModelOrden::class.java]
            viewModelOrden.consultarOrdenCliente(requireContext(), object : MainListener {
                override fun onSuccess(response: JsonArray) {
                    if (response != null) {
                        val jsonArrayString = response.toString()
                        val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray
                        listaOrden.clear()
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
                            listaID.add(idProducto)
                            orden
                        })
                        listaProd.forEach { producto ->
                            listaID.forEach { idProducto ->
                                if (producto.idProducto == idProducto) {
                                    val idTiendaS = producto.idTienda
                                    listaIDTienda.add(idTiendaS)
                                    sListaProducto.add(producto)
                                    listaTienda.forEach { tienda ->
                                        if (tienda.idTienda == idTiendaS) {
                                            sListaTienda.add(tienda)

                                        }
                                    }
                                }
                            }

                            cargarRecycler(listaOrden, sListaProducto, sListaTienda)

                        }

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

    private fun cargarRecycler(
        listaOrden: List<Orden>,
        sListaProducto: List<Producto>,
        sListaTienda: List<Tienda>
    ) {
        adatadorMisOrdenes = ClienteVistaMisOrdenesAdapter(
            listaOrden,
            sListaTienda,
            sListaProducto,
            this@MisOrdenesFragment
        )
        fbinding.rvMisOrdenes.layoutManager = LinearLayoutManager(requireContext())
        fbinding.rvMisOrdenes.adapter = adatadorMisOrdenes


    }

    private fun cargarDatosTienda() {
        val activity = activity
        if (isAdded && activity != null) {
            val viewModelTienda =
                ViewModelProvider(requireActivity())[ViewModelTienda::class.java]
            viewModelTienda.consultarTienda(requireContext(), object : MainListener {
                override fun onSuccess(response: JsonArray) {
                    if (response != null) {
                        val jsonArrayString = response.toString()
                        val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray
                        listaTienda.addAll(jsonArray.map { element ->
                            val jsonObject = element.asJsonObject
                            val id = jsonObject.get("id").asString.toInt()
                            val nombre = jsonObject.get("nombre").asString
                            val horaApertura =
                                convertStringToTime(jsonObject.get("horaApertura").asString)!!
                            val horaCierre =
                                convertStringToTime(jsonObject.get("horaCierre").asString)!!
                            val logoString = jsonObject.get("logo").asString
                            val logoByteArray = Base64.decode(logoString, Base64.DEFAULT)
                            val estado = jsonObject.get("estado").asBoolean

                            Tienda(id, nombre, logoByteArray, horaApertura, horaCierre, estado)
                        })

                        listaTienda
                        cargarDatosProductoParaTodasLasTiendas(listaTienda)
                    }else{
                        Toast.makeText(activity, "No hay Tiendas", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(error: String) {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }else{}

    }

    private fun cargarDatosProductoParaTodasLasTiendas(listaTienda: List<Tienda>) {
        val totalTiendas = listaTienda.size
        var tiendasProcesadas = 0

        listaTienda.forEach { tienda ->
            cargarDatosProducto(tienda.idTienda) {

                tiendasProcesadas++

                if (tiendasProcesadas == totalTiendas) {

                    cargarDatosOrdenes(listaTienda, listaProducto)
                }
            }

    }
    }


    fun cargarDatosProducto( idTienda: Int, completion: () -> Unit) {
        val activity = activity
        if (isAdded && activity != null) {
            val viewModelProducto =
                ViewModelProvider(requireActivity())[ViewModelProducto::class.java]
            viewModelProducto.consultarProducto(requireContext(), object : MainListener {
                override fun onSuccess(response: JsonArray) {

                    if (response != null) {
                        val jsonArrayString = response.toString()
                        val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray
                        listaProducto.addAll(jsonArray.mapNotNull { element ->
                            val jsonObject = element.asJsonObject
                            val id = jsonObject.get("id")?.asString?.toInt()
                            val nombre = jsonObject.get("nombre")?.asString
                            val precio = jsonObject.get("precio")?.asFloat
                            val logoString = jsonObject.get("imagen")?.asString
                            val logoByteArray =
                                logoString?.let { Base64.decode(it, Base64.DEFAULT) }
                            val descripcion = jsonObject.get("descripcion")?.asString
                            val idTienda = jsonObject.get("idTienda")?.asString?.toInt()
                            val disponibilidad = jsonObject.get("disponibilidad")?.asBoolean
                            val tiempoEstimado =
                                jsonObject.get("tiempoEstimado")?.asString?.toInt()

                            if (id != null && nombre != null && precio != null && logoByteArray != null && descripcion != null && idTienda != null && disponibilidad != null && tiempoEstimado != null) {
                                Producto(
                                    id,
                                    nombre,
                                    precio,
                                    logoByteArray,
                                    descripcion,
                                    disponibilidad,
                                    tiempoEstimado,
                                    idTienda
                                )
                            } else {
                                null
                            }
                        })

                        //cargarDatosOrdenes(listaTienda, listaProducto)


                    } else {
                        Toast.makeText(activity, "No hay Producto", Toast.LENGTH_LONG).show()
                    }
                    completion()

                }

                override fun onFailure(error: String) {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                    }
                }
            }, idTienda)
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

    override fun onSelectItemClick(
        orden: Orden,
        producto: Producto,
        tienda: Tienda
    ) {
        val action = MisOrdenesFragmentDirections.actionMisOrdenesFragmentToOrdenEspecificaFragment(orden, producto, tienda)
        findNavController().navigate(action)

    }


}