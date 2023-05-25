package com.fao.orderfy.presentacion.fragmentos

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentContinuarCrearCuentaBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.repositorio.RepositorioCliente
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.google.gson.JsonArray

class ContinuarCrearCuentaFragment : Fragment() {
    private lateinit var fbinding: FragmentContinuarCrearCuentaBinding
    lateinit var room: BD
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentContinuarCrearCuentaBinding.inflate(layoutInflater)

        iniciar()
        return fbinding.root
    }


    private fun iniciar() {
        validarUserName()
        fbinding.tvIniciarSesionCC.setOnClickListener {
            Navigation.findNavController(fbinding.root)
                .navigate(R.id.action_continuarCrearCuentaFragment_to_seleccionUsuarioLoginFragment)
        }
        fbinding.btnCrearCuentaC.setOnClickListener {
            if (validarEditText()) {
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            } else if (validarPWD() && fbinding.etUserName.text.toString() != "admin") {
                registrarCliente()
                requireArguments().clear()
                Navigation.findNavController(fbinding.root)
                    .navigate(R.id.action_continuarCrearCuentaFragment_to_seleccionUsuarioLoginFragment)


            } else if (fbinding.etUserName.text.toString() == "admin") {
                fbinding.etUserName.setText("")
                Toast.makeText(activity, "Usuario admin no esta permitido", Toast.LENGTH_LONG)
                    .show()
            } else if (!validarPWD()) {
                fbinding.etPassword.setText("")
                fbinding.etConfirmPassword.setText("")
                Toast.makeText(activity, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            }

        }


    }

    private fun registrarCliente() {

        var bd = BD.getDatabase(requireContext()).DaoCliente()
        var nombre = requireArguments().getString("nombreCliente").toString()
        var apellido = requireArguments().getString("apellidoCliente").toString()
        var usuario = fbinding.etUserName.text.toString()
        var pwd = fbinding.etPassword.text.toString()
        var confirmPWD = fbinding.etConfirmPassword.text.toString()
        val cliente = Cliente(0, nombre, apellido, usuario, pwd)

        var viewModelCliente = ViewModelProvider(this)[ViewModelCliente::class.java]
        viewModelCliente.registrarCliente(object : MainListener {
            override fun onSuccess(response: JsonArray) {

                Toast.makeText(activity, "Cliente Registrado Correctamente", Toast.LENGTH_LONG)
                    .show()

            }

            override fun onFailure(error: String) {
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG)
                    .show()
            }

        }, cliente)

    }

    private fun validarUserName() {
        var userName = fbinding.etUserName
        userName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val userInput = userName.text.toString()
                if (userInput == "admin") {
                    userName.text.clear()
                }
            }
        }
    }

    private fun validarEditText(): Boolean {
        var validar = false
        if (fbinding.etPassword.text.toString() == "" || fbinding.etUserName.text.toString() == "" || fbinding.etUserName.text.toString() == "") {
            validar = true
            return validar
        } else {
            validar = false
            return validar
        }
    }

    private fun limpiarCampos() {
        fbinding.etUserName.setText("")
        fbinding.etPassword.setText("")
        fbinding.etConfirmPassword.setText("")

    }

    private fun validarPWD(): Boolean {
        var validar = false
        var pwd = fbinding.etPassword.text.toString()
        var confirmPWD = fbinding.etConfirmPassword.text.toString()
        if (pwd == confirmPWD) {
            validar = true
            return validar
        } else {
            validar = false
            return validar
        }
    }

}