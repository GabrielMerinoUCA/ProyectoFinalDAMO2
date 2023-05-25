package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentAdministradorVistaSolucitudesBinding


class AdministradorVistaSolucitudesFragment : Fragment() {
    private lateinit var fbinding: FragmentAdministradorVistaSolucitudesBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentAdministradorVistaSolucitudesBinding.inflate(layoutInflater)
        return fbinding.root
    }


}