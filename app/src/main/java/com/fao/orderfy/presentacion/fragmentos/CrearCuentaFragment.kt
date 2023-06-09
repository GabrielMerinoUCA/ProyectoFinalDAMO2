package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentCrearCuentaBinding


class CrearCuentaFragment : Fragment() {
    private lateinit var fbinding: FragmentCrearCuentaBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentCrearCuentaBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.tvIniciarSesionC.setOnClickListener {
            Navigation.findNavController(fbinding.root)
                .navigate(R.id.action_crearCuentaFragment_to_seleccionUsuarioLoginFragment)

        }
        fbinding.btnContinuar.setOnClickListener {
            if (validarEditText() == true) {
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            } else {
                var nombre = fbinding.etNombre.text.toString()
                var apellido = fbinding.etApellido.text.toString()
                Navigation.findNavController(fbinding.root).navigate(
                    R.id.action_crearCuentaFragment_to_continuarCrearCuentaFragment,
                    Bundle().apply {
                        putString("nombreCliente", nombre)
                        putString("apellidoCliente", apellido)
                    })
                limpiarCampos()
            }

        }


    }

    private fun validarEditText(): Boolean {
        var validar = false
        if (fbinding.etNombre.text.toString() == "" || fbinding.etApellido.text.toString() == "") {
            validar = true
            return validar
        } else {
            validar = false
            return validar
        }
    }

    private fun limpiarCampos(){
        fbinding.etNombre.setText("")
        fbinding.etApellido.setText("")

    }



}