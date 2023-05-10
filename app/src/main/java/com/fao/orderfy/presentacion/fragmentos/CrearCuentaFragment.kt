package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        fbinding.tvIniciarSesionC.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_crearCuentaFragment_to_iniciarSesionFragment)
        }
        fbinding.btnContinuar.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_crearCuentaFragment_to_continuarCrearCuentaFragment)
        }
        return fbinding.root
    }

}