package com.fao.orderfy.presentacion.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentIniciarSesionVendedorBinding
import com.fao.orderfy.presentacion.actividades.VendedorActivity


class IniciarSesionVendedorFragment : Fragment() {
    private lateinit var fbinding: FragmentIniciarSesionVendedorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentIniciarSesionVendedorBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.tvRegistrar.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_iniciarSesionVendedorFragment_to_registroFragment)
        }
        fbinding.btnIniciarSesionC.setOnClickListener {
            var intent = Intent(activity, VendedorActivity::class.java)
            startActivity(intent)
        }
    }


}