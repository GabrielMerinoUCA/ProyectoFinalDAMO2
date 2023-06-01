package com.fao.orderfy.presentacion.fragmentos

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentHomeBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.remoto.api.RetrofitService
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.fao.orderfy.presentacion.viewmodel.ViewModelVendedor
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var fbinding: FragmentHomeBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentHomeBinding.inflate(layoutInflater)
        iniciar()
        cargarDatos()
        return fbinding.root
    }

    private fun cargarDatos() {
        if (RetrofitService.isServerReachable(requireContext())) {
            coroutineScope.launch {
                cargarDatosCliente(requireContext())
            }
        } else {
            Toast.makeText(activity, "No tiene conexion a internet", Toast.LENGTH_LONG).show()
        }
    }

    private fun iniciar() {
        fbinding.btnCrearCuenta.setOnClickListener {
            if (RetrofitService.isServerReachable(requireContext())) {
                Navigation.findNavController(fbinding.root).navigate(R.id.action_homeFragment_to_registroFragment)
            } else {
                Toast.makeText(activity, "No tiene conexion a internet", Toast.LENGTH_LONG).show()
            }

        }
        fbinding.btnIniciarSesion.setOnClickListener {

            Navigation.findNavController(fbinding.root).navigate(R.id.action_homeFragment_to_seleccionUsuarioLoginFragment)



        }
    }

    companion object{

        private val coroutineScope = CoroutineScope(Dispatchers.Main)
        //private lateinit var applicationContext: Context
        suspend fun cargarDatosCliente(context: Context) {
            var bd = BD.getDatabase(context)
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
                        val cliente = Cliente(id, nombre, apellido, nombreUsuario, pwd)
                        coroutineScope.launch {
                            var clienteUnico =
                                bd.DaoCliente().contarUsuarioPorNombre(cliente.nombreUsuario)
                            var cantidad = bd.DaoCliente().getCount()
                            if (cantidad == 0) {

                                registrarDatosCliente(cliente, context)

                            } else if (clienteUnico == 0) {
                                registrarDatosCliente(cliente, context)
                            }

                        }


                        // Aquí puedes hacer lo que desees con las variables
                        // Por ejemplo, puedes almacenar los datos en una lista, en objetos, etc.
                    }
                }

                override fun onFailure(error: String) {

                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    }

            })
        }

        fun registrarDatosCliente(cliente: Cliente, context: Context) {

            coroutineScope.launch {
                var bd = BD.getDatabase(context)
                //var bd = BD.getDatabase(this@MainActivity).DaoCliente()
                bd.DaoCliente().registrarCliente(
                    cliente.nombre,
                    cliente.apellido,
                    cliente.nombreUsuario,
                    cliente.pwd
                )
            }
        }
    }

}