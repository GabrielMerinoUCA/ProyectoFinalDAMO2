package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.databinding.FragmentAdministradorVistaSolucitudesBinding
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.RegistroListener
import com.fao.orderfy.presentacion.adapters.RegistroAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelRegistro
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import java.sql.Time
import java.text.SimpleDateFormat


class AdministradorHomeFragment : Fragment(), RegistroListener {

    private lateinit var fbinding: FragmentAdministradorVistaSolucitudesBinding
    private lateinit var viewModelRegistro: ViewModelRegistro
    private lateinit var registroList: ArrayList<Registro>
    private lateinit var registroPendienteList: ArrayList<Registro>
    private lateinit var adaptadorRegistro: RegistroAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        viewModelRegistro = ViewModelProvider(this)[ViewModelRegistro::class.java]
        fbinding = FragmentAdministradorVistaSolucitudesBinding.inflate(layoutInflater)
        cargarDatosSolicitud()
        return fbinding.root

    }

    private fun cargarDatosSolicitud() {
        registroList = ArrayList()
        registroPendienteList = ArrayList()
        val viewModelRegistro =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelRegistro::class.java]
        viewModelRegistro.consultarRegistro(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val jsonArrayString = response.toString()
                val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray
                for (element in jsonArray) {
                    val jsonObject = element.asJsonObject
                    val idRegistro = jsonObject.get("idRegistro").asString.toInt()
                    val nombre = jsonObject.get("nombre").asString
                    val apellido = jsonObject.get("apellido").asString
                    val nombreTienda = jsonObject.get("nombreTienda").asString
                    val nombreUsuario = jsonObject.get("nombreUsuario").asString
                    val horaApertura = convertStringToTime(jsonObject.get("horaApertura").asString)
                    val horaCierre = convertStringToTime(jsonObject.get("horaCierre").asString)
                    val pwd = jsonObject.get("pwd").asString
                    val estado = jsonObject.get("estado").asString.toInt()
                    val registro = Registro(
                        idRegistro,
                        nombre,
                        apellido,
                        nombreUsuario,
                        nombreTienda,
                        pwd,
                        horaApertura!!,
                        horaCierre!!,
                        estado
                    )
                    registroList.add(registro)

                }
                registroList.forEach { registro ->
                    if (registro.estado == 0) {
                        registroPendienteList.add(registro)
                    }
                }
                adaptadorRegistro =
                    RegistroAdapter(registroPendienteList , this@AdministradorHomeFragment)
                fbinding.rvSolicitudes.layoutManager = LinearLayoutManager(requireContext())
                fbinding.rvSolicitudes.adapter = adaptadorRegistro

            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }
        })
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

    override fun onSelectedItem(registro: Registro) {
        val action =
            AdministradorHomeFragmentDirections.actionAdministradorHomeFragmentToAdministradorVistaSolicitudEspFragment(
                registro
            )
        findNavController().navigate(action)

    }


}