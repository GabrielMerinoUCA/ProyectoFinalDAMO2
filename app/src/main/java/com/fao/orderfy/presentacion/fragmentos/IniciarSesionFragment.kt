package com.fao.orderfy.presentacion.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentIniciarSesionBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.local.dao.DaoCliente
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.AdminActivity
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.actividades.MainActivity
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class IniciarSesionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var fbinding: FragmentIniciarSesionBinding
    private var clientes: ArrayList<Cliente> = ArrayList()
    private var clientesList: MutableList<Cliente> = mutableListOf()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onResume() {
        super.onResume()
        if (RetrofitService.isServerReachable(requireContext())) {
            cargarDatosCliente(requireContext())
        } else {
            cargarDatosLocal()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentIniciarSesionBinding.inflate(layoutInflater)
        inicio()
        return fbinding.root
    }

    private fun inicio() {

        fbinding.tvRegistrar.setOnClickListener {
            Navigation.findNavController(fbinding.root)
                .navigate(R.id.action_iniciarSesionFragment_to_registroFragment)
        }
        fbinding.btnIniciarSesionC.setOnClickListener {
            if (fbinding.etUser.text.toString() == "" || fbinding.etPWD.text.toString() == "") {
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            } else {
                validarLogin()
            }

        }
    }

    private fun validarLogin() {
        var userName = fbinding.etUser.text.toString()
        var pwd = fbinding.etPWD.text.toString()
        var cliente = Cliente(0, "", "", userName, pwd)
        var viewModelCliente = ViewModelProvider(this)[ViewModelCliente::class.java]
        viewModelCliente.autenticarCliente(requireContext(), object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val valor = validarJsonArray(response)
                if (valor.asBoolean) {
                    var intent = Intent(activity, ClienteActivity::class.java)
                    var sesion = Cliente(0, "", "", userName, pwd)
                    for (x in clientes) {
                        if (x.nombreUsuario == cliente.nombreUsuario && x.pwd == cliente.pwd) {
                            sesion = x
                        }
                    }
                    intent.putExtra("SesionCliente", sesion)
                    limpiarDatos()

                    startActivity(intent)

                } else {
                    Toast.makeText(
                        activity,
                        "Usuario no encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                }





            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, cliente)
    }

    private fun limpiarDatos(){
        fbinding.etUser.setText("")
        fbinding.etPWD.setText("")
    }

    fun cargarDatosCliente(context: Context) {
        val viewModelCliente = ViewModelProvider(context as ViewModelStoreOwner)[ViewModelCliente::class.java]
        viewModelCliente.consultarCliente(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val jsonArrayString = response.toString()
                val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray
                for (element in jsonArray) {
                    val jsonObject = element.asJsonObject
                    val id = jsonObject.get("id").asString.toInt()
                    val nombre = jsonObject.get("nombre").asString
                    val apellido = jsonObject.get("apellido").asString
                    val nombreUsuario = jsonObject.get("nombreUsuario").asString
                    val pwd = jsonObject.get("pwd").asString
                    clientes.add(Cliente(id, nombre, apellido, nombreUsuario, pwd))
                }
            }

            override fun onFailure(error: String) {

                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        })
    }
    private fun cargarDatosLocal(){
        coroutineScope.launch {
            clientesList = BD.getDatabase(requireContext()).DaoCliente().consultarCliente()
        }

    }

    fun validarJsonArray(jsonArray: JsonArray): JsonElement {
        val jsonObject = jsonArray.get(0).asJsonObject
        return jsonObject.get("response")
    }



}