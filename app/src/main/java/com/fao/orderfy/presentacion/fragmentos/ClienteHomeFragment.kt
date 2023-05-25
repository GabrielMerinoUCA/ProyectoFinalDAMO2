package com.fao.orderfy.presentacion.fragmentos

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.R
import com.fao.orderfy.presentacion.adapters.PopularProdAdapter
import com.fao.orderfy.databinding.FragmentClienteHomeBinding
import java.time.LocalTime

class ClienteHomeFragment : Fragment() {

    private lateinit var fbinding: FragmentClienteHomeBinding
    var listaProd: MutableList<Producto> = mutableListOf()
    lateinit var adatador: PopularProdAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentClienteHomeBinding.inflate(layoutInflater)

        inicio()


        return fbinding.root
    }


    private fun inicio() {
        fbinding.ivPrueba.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_clienteHomeFragment_to_clienteLocalVistaFragment)
        }
    }


}