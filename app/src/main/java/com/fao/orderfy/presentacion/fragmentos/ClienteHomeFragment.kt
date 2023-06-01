package com.fao.orderfy.presentacion.fragmentos

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.R
import com.fao.orderfy.presentacion.adapters.PopularProdAdapter
import com.fao.orderfy.databinding.FragmentClienteHomeBinding
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.ClienteVistaTiendasListener
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.adapters.ClienteVistaTiendaAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.fao.orderfy.presentacion.viewmodel.ViewModelTienda
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.json.JSONArray
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalTime

class ClienteHomeFragment : Fragment(), ClienteVistaTiendasListener {

    private lateinit var fbinding: FragmentClienteHomeBinding
    var listaProd: MutableList<Producto> = mutableListOf()
    var listaTienda: MutableList<Tienda> = mutableListOf()
    lateinit var adatadorTienda: ClienteVistaTiendaAdapter
    lateinit var adatador: PopularProdAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentClienteHomeBinding.inflate(layoutInflater)

        inicio()


        return fbinding.root
    }


    private fun inicio() {
        /*fbinding.ivPrueba.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_clienteHomeFragment_to_clienteLocalVistaFragment)
        }*/
        fbinding.rvLocal.layoutManager = LinearLayoutManager(requireContext())
        obtenerDatosTienda()
    }

    private fun obtenerDatosTienda() {

        listaTienda.clear()
        var viewModelTienda = ViewModelProvider(this)[ViewModelTienda::class.java]
        viewModelTienda.consultarTienda(requireContext(), object : MainListener {
            override fun onSuccess(response: JsonArray) {
                if (response != null) {
                    val jsonArrayString = response.toString()
                    val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray

                    for (element in jsonArray) {
                        // Obtener el objeto en la posición actual
                        val jsonObject = element.asJsonObject
                        val logoBase64 = jsonObject.get("logo").asString
                        val logoByteArray = Base64.decode(logoBase64, Base64.DEFAULT)
                        val tienda = Tienda(
                            idTienda = jsonObject.get("id").asString.toInt(),
                            nombre = jsonObject.get("nombre").asString,
                            logo = logoByteArray,
                            horaApertura = convertStringToTime(jsonObject.get("horaApertura").asString)!!,
                            horaCierre = convertStringToTime(jsonObject.get("horaCierre").asString)!!,
                            estado = jsonObject.get("estado").asBoolean
                        )
                        // Agregar el objeto a la lista
                        listaTienda.add(tienda)
                    }
                    verificarLista()
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

    }

    private fun verificarLista(){
        var mostrarTodasLasTiendas = false
        if (listaTienda.size >= 3) {
            val tiendasSeleccionadas =
                listaTienda.subList(0, 3) // Obtener las primeras 3 tiendas

            adatadorTienda =
                ClienteVistaTiendaAdapter(tiendasSeleccionadas, requireContext(), this@ClienteHomeFragment)
            fbinding.rvLocal.adapter = adatadorTienda

            fbinding.tvVerTodos.setOnClickListener {
                if (mostrarTodasLasTiendas) {
                    // Si ya se están mostrando todas las tiendas, volver a mostrar solo tres tiendas
                    fbinding.tvVerTodos.setTextColor(Color.parseColor("#175676"))
                    adatadorTienda =
                        ClienteVistaTiendaAdapter(tiendasSeleccionadas, requireContext(), this@ClienteHomeFragment)

                } else {
                    // Mostrar todas las tiendas
                    fbinding.tvVerTodos.setTextColor(Color.GRAY)
                    adatadorTienda =
                        ClienteVistaTiendaAdapter(listaTienda, requireContext(), this@ClienteHomeFragment)
                }
                fbinding.rvLocal.adapter = adatadorTienda

                // Alternar el estado actual
                mostrarTodasLasTiendas = !mostrarTodasLasTiendas
            }
        } else {
            // Manejar el caso cuando hay menos de 3 tiendas en la lista
            // Puedes mostrar un mensaje o tomar alguna otra acción


            adatadorTienda =
                ClienteVistaTiendaAdapter(listaTienda, requireContext(), this@ClienteHomeFragment)
            fbinding.rvLocal.adapter = adatadorTienda

            fbinding.tvVerTodos.setOnClickListener {
                Toast.makeText(activity, "No hay mas Tiendas", Toast.LENGTH_SHORT).show()
            }
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

    override fun onSelectItemClick(tienda: Tienda) {

        val action = ClienteHomeFragmentDirections.actionClienteHomeFragmentToClienteLocalVistaFragment(tienda)
        findNavController().navigate(action)

    }


}