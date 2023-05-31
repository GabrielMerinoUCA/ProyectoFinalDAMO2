package com.fao.orderfy.presentacion.fragmentos


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.databinding.FragmentAdministradorVistaUsuarioBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.utils.ClienteListener
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.datos.utils.VendedorListener
import com.fao.orderfy.presentacion.adapters.UsuarioAdapter
import com.fao.orderfy.presentacion.adapters.VendedorAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.fao.orderfy.presentacion.viewmodel.ViewModelVendedor
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdministradorVistaUsuarioFragment : Fragment(), ClienteListener, VendedorListener {

    private lateinit var fbinding: FragmentAdministradorVistaUsuarioBinding
    private lateinit var viewModelCliente: ViewModelCliente
    private lateinit var clienteList: ArrayList<Cliente>
    private lateinit var vendedorList: ArrayList<Vendedor>
    private lateinit var adaptadorVendedor: VendedorAdapter
    private lateinit var adaptadorCliente: UsuarioAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentAdministradorVistaUsuarioBinding.inflate(layoutInflater)
        viewModelCliente = ViewModelProvider(this)[ViewModelCliente::class.java]
        cargarDatosVendedor()
        cargarDatosCliente()
        return fbinding.root
    }

    fun cargarDatosCliente() {
        clienteList = ArrayList()
        val viewModelCliente =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelCliente::class.java]
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
                    clienteList.add(cliente)


                }

                adaptadorCliente =
                    UsuarioAdapter(clienteList, this@AdministradorVistaUsuarioFragment)
                fbinding.rvCliente.layoutManager = LinearLayoutManager(requireContext())
                fbinding.rvCliente.adapter = adaptadorCliente
            }

            override fun onFailure(error: String) {

                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    fun cargarDatosVendedor() {
        vendedorList = ArrayList()
        val viewModelVendedor = ViewModelProvider(this)[ViewModelVendedor::class.java]
        val context = requireContext()

        viewModelVendedor.consultarVendedor(context, object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val jsonArrayString = response.toString()
                val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray
                for (element in jsonArray) {
                    val jsonObject = element.asJsonObject
                    val id = jsonObject.get("id").asString.toInt()
                    val nombreUsuario = jsonObject.get("nombreUsuario").asString
                    val pwd = jsonObject.get("pwd").asString
                    val idTienda = jsonObject.get("idTienda").asString.toInt()
                    val vendedor = Vendedor(idTienda, id, nombreUsuario, pwd)
                    vendedorList.add(vendedor)
                }
                adaptadorVendedor =
                    VendedorAdapter(vendedorList, this@AdministradorVistaUsuarioFragment)
                fbinding.rvVendedor.layoutManager = LinearLayoutManager(context)
                fbinding.rvVendedor.adapter = adaptadorVendedor
            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    override fun onDeleteItem(cliente: Cliente) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Eliminar Cliente")
        dialog.setMessage("¿Desea eliminar este Cliente?")
        dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
            eliminarCliente(cliente)
            eliminarLocalCliente(cliente)


        })
        dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
            Toast.makeText(requireContext(), "Eliminacion Cancelada", Toast.LENGTH_SHORT).show()
        })
        dialog.show()

    }


    private fun eliminarCliente(cliente: Cliente) {
        val viewModelCliente =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelCliente::class.java]
        viewModelCliente.eliminarCliente(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                Toast.makeText(activity, "Cliente Eliminado", Toast.LENGTH_LONG).show()
                cargarDatosCliente()
            }

            override fun onFailure(error: String) {

                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, cliente)

    }

    private fun eliminarVendedor(vendedor: Vendedor) {
        val viewModelVendedor =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelVendedor::class.java]
        viewModelVendedor.eliminarVendedor(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                Toast.makeText(activity, "Vendedor Eliminado", Toast.LENGTH_LONG).show()
                cargarDatosVendedor()
            }

            override fun onFailure(error: String) {

                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, vendedor)

    }

    private fun eliminarLocalCliente(cliente: Cliente) {
        coroutineScope.launch {
            var bd = BD.getDatabase(requireContext())
            var id = bd.DaoCliente().obtenerIdPorNombreUsuario(cliente.nombreUsuario)
            bd.DaoCliente().eliminarCliente(id)

        }

    }

    override fun onDeleteVendedor(vendedor: Vendedor) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Eliminar Vendedor")
        dialog.setMessage("¿Desea eliminar este Vendedor?")
        dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
            eliminarVendedor(vendedor)
            Log.wtf("Error: ", "${vendedor.idVendedor}")
        })
        dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
            Toast.makeText(requireContext(), "Eliminacion Cancelada", Toast.LENGTH_SHORT).show()
        })
        dialog.show()
    }


}