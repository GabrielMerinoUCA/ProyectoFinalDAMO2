package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentVendedorMenuBinding


class VendedorMenuFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorMenuBinding.inflate(layoutInflater)
        fbinding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_vendedorMenuFragment_to_vendedorAgregarProdFragment)
        }
        return fbinding.root
    }


}