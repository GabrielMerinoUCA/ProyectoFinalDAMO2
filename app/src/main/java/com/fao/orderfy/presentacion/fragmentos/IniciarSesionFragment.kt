package com.fao.orderfy.presentacion.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentIniciarSesionBinding
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.actividades.VendedorActivity


class IniciarSesionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var fbinding: FragmentIniciarSesionBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentIniciarSesionBinding.inflate(layoutInflater)
        fbinding.tvRegistrar.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_iniciarSesionFragment_to_registroFragment)
        }
        fbinding.btnIniciarSesionC.setOnClickListener {
            var intent = Intent(activity, ClienteActivity::class.java)
            startActivity(intent)
        }
        return fbinding.root
    }


}