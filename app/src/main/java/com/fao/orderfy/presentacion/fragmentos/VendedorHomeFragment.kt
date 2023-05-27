package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fao.orderfy.databinding.FragmentVendedorHomeBinding
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.presentacion.actividades.VendedorActivity


class VendedorHomeFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorHomeBinding
    private lateinit var sesionVendedor: Vendedor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorHomeBinding.inflate(layoutInflater)
        sesionVendedor = VendedorActivity.sesionVendedorGlobal!!
        return fbinding.root
    }



}