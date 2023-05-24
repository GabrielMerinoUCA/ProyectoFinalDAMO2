package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentRegistroVendedorBinding

class RegistroVendedorFragment : Fragment() {
    private lateinit var fbinding: FragmentRegistroVendedorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentRegistroVendedorBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.tvIniciarSesionCC.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_registroVendedorFragment_to_seleccionUsuarioLoginFragment)
        }
        fbinding.btnContinuar.setOnClickListener {
            if (validarEditText()) {
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            } else if (validarPWD() && fbinding.etUserName.text.toString() != "admin") {
                var nombre = requireArguments().getString("nombre").toString()
                var apellido = requireArguments().getString("apellido").toString()
                var userName = fbinding.etUserName.text.toString()
                var pwd = fbinding.etPassword.text.toString()
                var confirmPWD = fbinding.etConfirmPassword.text.toString()
                Navigation.findNavController(fbinding.root).navigate(R.id.action_registroVendedorFragment_to_continuarRegistroVendedorFragment, Bundle().apply {
                    putString("userName", userName)
                    putString("pwd", pwd)
                    putString("confirmPWD", confirmPWD)
                    putString("nombre", nombre)
                    putString("apellido", apellido)
                })
                requireArguments().clear()

            } else if (fbinding.etUserName.text.toString() == "admin"){
                fbinding.etUserName.setText("")
                Toast.makeText(activity, "Usuario admin no esta permitido", Toast.LENGTH_LONG).show()
            }else if (!validarPWD()){
                fbinding.etPassword.setText("")
                fbinding.etConfirmPassword.setText("")
                Toast.makeText(activity, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun validarEditText(): Boolean {
        var validar = false
        if (fbinding.etUserName.text.toString() == "" || fbinding.etPassword.text.toString() == "" || fbinding.etConfirmPassword.text.toString() == "") {
            validar = true
            return validar
        } else {
            validar = false
            return validar
        }
    }

    private fun limpiarCampos(){
        fbinding.etUserName.setText("")
        fbinding.etPassword.setText("")
        fbinding.etConfirmPassword.setText("")

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