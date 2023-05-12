package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fao.orderfy.databinding.FragmentEditarPerfilBinding

class EditarPerfilFragment : Fragment() {
    private lateinit var fbinding: FragmentEditarPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentEditarPerfilBinding.inflate(layoutInflater)
        return fbinding.root
    }

}