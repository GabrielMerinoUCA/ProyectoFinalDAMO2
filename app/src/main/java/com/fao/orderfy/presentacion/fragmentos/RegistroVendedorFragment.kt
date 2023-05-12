package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        fbinding.btnContinuar.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_registroVendedorFragment_to_continuarRegistroVendedorFragment)
        }
        return fbinding.root
    }

}