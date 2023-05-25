package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentHomeBinding
import com.fao.orderfy.datos.remoto.api.RetrofitService


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
            if (RetrofitService.isServerReachable(requireContext())) {
                Navigation.findNavController(fbinding.root).navigate(R.id.action_homeFragment_to_registroFragment)
            } else {
                Toast.makeText(activity, "No tiene conexion a internet", Toast.LENGTH_LONG).show()
            }

        }
        fbinding.btnIniciarSesion.setOnClickListener {

            Navigation.findNavController(fbinding.root).navigate(R.id.action_homeFragment_to_seleccionUsuarioLoginFragment)



        }
    }


}