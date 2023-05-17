package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fao.orderfy.databinding.FragmentVendedorOrdenesBinding


class VendedorOrdenesFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorOrdenesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorOrdenesBinding.inflate(layoutInflater)
        return fbinding.root
    }


}