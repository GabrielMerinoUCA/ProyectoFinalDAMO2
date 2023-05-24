package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var fbinding: FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentHomeBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.btnCrearCuenta.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_homeFragment_to_registroFragment)
        }
        fbinding.btnIniciarSesion.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_homeFragment_to_seleccionUsuarioLoginFragment)



        }
    }


}