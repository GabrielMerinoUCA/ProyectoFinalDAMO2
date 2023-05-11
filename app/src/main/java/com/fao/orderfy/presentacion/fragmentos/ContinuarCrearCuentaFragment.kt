package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentContinuarCrearCuentaBinding

class ContinuarCrearCuentaFragment : Fragment() {
    private lateinit var fbinding: FragmentContinuarCrearCuentaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentContinuarCrearCuentaBinding.inflate(layoutInflater)
        fbinding.tvIniciarSesionCC.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_continuarCrearCuentaFragment_to_iniciarSesionFragment)
        }
        fbinding.btnCrearCuentaC.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_continuarCrearCuentaFragment_to_iniciarSesionFragment)
        }
        return fbinding.root
    }
}