package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fao.orderfy.databinding.FragmentVendedorAgregarProdBinding


class VendedorAgregarProdFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorAgregarProdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorAgregarProdBinding.inflate(layoutInflater)
        return fbinding.root
    }


}