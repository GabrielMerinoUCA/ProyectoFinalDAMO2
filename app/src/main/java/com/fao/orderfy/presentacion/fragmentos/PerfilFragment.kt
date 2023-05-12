package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {
    private lateinit var fbinding: FragmentPerfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentPerfilBinding.inflate(layoutInflater)
        fbinding.btnEditarPerfil.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_perfilFragment_to_editarPerfilFragment)
        }
        return fbinding.root
    }
}