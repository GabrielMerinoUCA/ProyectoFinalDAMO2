package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentSeleccionUsuarioLoginBinding


class SeleccionUsuarioLoginFragment : Fragment() {
    private lateinit var fbinding: FragmentSeleccionUsuarioLoginBinding
    private var selectedButton: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentSeleccionUsuarioLoginBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        validarBotonSelecionado()
        fbinding.tvRegistrateC.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_seleccionUsuarioLoginFragment_to_registroFragment)
        }
    }

    private fun validarBotonSelecionado() {
        fbinding.btnCliente.setOnClickListener {
            clearButtonSelection()
            selectedButton = fbinding.btnCliente
            fbinding.btnCliente.setBackgroundColor(Color.parseColor("#CCE6F4"))
            fbinding.btnVendedor.setBackgroundColor(Color.parseColor("#D9D9D9"))
        }
        fbinding.btnVendedor.setOnClickListener {
            clearButtonSelection()
            selectedButton = fbinding.btnVendedor
            fbinding.btnVendedor.setBackgroundColor(Color.parseColor("#CCE6F4"))
            fbinding.btnCliente.setBackgroundColor(Color.parseColor("#D9D9D9"))
        }
        fbinding.btnContinuar.setOnClickListener {
            if (selectedButton == fbinding.btnCliente) {
                // Navega al fragmento para el botón 1
                Navigation.findNavController(fbinding.root).navigate(R.id.action_seleccionUsuarioLoginFragment_to_iniciarSesionFragment)

            } else if (selectedButton == fbinding.btnVendedor) {
                Navigation.findNavController(fbinding.root).navigate(R.id.action_seleccionUsuarioLoginFragment_to_iniciarSesionVendedorFragment)

            } else {
                // Ningún botón está seleccionado
                Toast.makeText(activity, "Seleccione un botón primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearButtonSelection() {
        // Deselecciona cualquier botón que se haya seleccionado previamente
        if (selectedButton != null) {
            selectedButton?.isSelected = false
            selectedButton = null
        }
    }


}