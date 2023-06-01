package com.fao.orderfy.presentacion.fragmentos

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentEditarPerfilBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditarPerfilFragment : Fragment() {
    private lateinit var fbinding: FragmentEditarPerfilBinding
    private lateinit var sesionCliente: Cliente
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentEditarPerfilBinding.inflate(layoutInflater)
        sesionCliente = ClienteActivity.sesionClienteGlobal!!
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        cargarDatos()
        fbinding.btnSEditarPerfil.setOnClickListener {
            confirmEditarCliente()
        }

    }

    private fun validarEdicion() {
        var pwd = ""
        if (validarEditText()) {
            Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                .show()
        } else if (fbinding.etEditPassword.text.toString() == "" && fbinding.etEditUserName.text.toString() != "admin") {
            pwd = sesionCliente.pwd
            editarCliente(pwd)

        } else if ((validarPWD() && fbinding.etEditUserName.text.toString() != "admin")) {
            pwd = fbinding.etEditPassword.text.toString()
            editarCliente(pwd)

        } else if (fbinding.etEditUserName.text.toString() == "admin") {
            fbinding.etEditUserName.setText("")
            Toast.makeText(activity, "Usuario admin no esta permitido", Toast.LENGTH_LONG)
                .show()
        } else if (!validarPWD()) {
            fbinding.etEditPassword.setText("")
            fbinding.etEditConfirmPassword.setText("")
            Toast.makeText(activity, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        }
    }

    private fun confirmEditarCliente() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Editar Cliente")
        dialog.setMessage("¿Desea editar este Cliente?")
        dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
            validarEdicion()

        })
        dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
            Toast.makeText(requireContext(), "Edición Cancelada", Toast.LENGTH_SHORT).show()
        })
        dialog.show()
    }

    private fun editarCliente(string: String) {
        var nombre = fbinding.etEditNombre.text.toString()
        var apellido = fbinding.etEditApellido.text.toString()
        var usuario = fbinding.etEditUserName.text.toString()
        var pwd = string
        val cliente = Cliente(sesionCliente.idCliente, nombre, apellido, usuario, pwd)

        var viewModelCliente = ViewModelProvider(this)[ViewModelCliente::class.java]
        viewModelCliente.editarCliente(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                coroutineScope.launch {
                    editarClienteLocal(cliente)
                }
                Toast.makeText(
                    requireContext(),
                    "Cliente Actualizado Correctamente",
                    Toast.LENGTH_LONG
                )
                    .show()
                activity?.finish()


            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, cliente)
    }

    private fun editarClienteLocal(cliente: Cliente) {

        coroutineScope.launch {
            var bd = BD.getDatabase(requireContext())
            var id = bd.DaoCliente().obtenerIdPorNombreUsuario(sesionCliente.nombreUsuario)
            bd.DaoCliente().editarCliente(id,
                cliente.nombre,
                cliente.apellido,
                cliente.nombreUsuario,
                cliente.pwd
            )
        }
}


private fun cargarDatos() {
    with(fbinding) {
        etEditNombre.setText(sesionCliente.nombre)
        etEditApellido.setText((sesionCliente.apellido))
        etEditUserName.setText(sesionCliente.nombreUsuario)

    }
}

private fun validarEditText(): Boolean {
    var validar = false
    if (fbinding.etEditNombre.text.toString() == "" || fbinding.etEditApellido.text.toString() == "" || fbinding.etEditUserName.text.toString() == "") {
        validar = true
        return validar
    } else {
        validar = false
        return validar
    }
}

private fun validarPWD(): Boolean {
    var validar = false
    var pwd = fbinding.etEditPassword.text.toString()
    var confirmPWD = fbinding.etEditConfirmPassword.text.toString()
    if (pwd == confirmPWD) {
        validar = true
        return validar
    } else {
        validar = false
        return validar
    }
}

}