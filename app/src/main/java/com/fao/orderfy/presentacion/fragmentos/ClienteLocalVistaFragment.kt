package com.fao.orderfy.presentacion.fragmentos

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.R
import com.fao.orderfy.presentacion.adapters.ProdAdapter
import com.fao.orderfy.databinding.FragmentClienteLocalVistaBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.time.LocalTime


class ClienteLocalVistaFragment : Fragment() {
    private lateinit var fbinding: FragmentClienteLocalVistaBinding
    private lateinit var bottomSheet: LinearLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    var listaProd: MutableList<Producto> = mutableListOf()
    lateinit var adatador: ProdAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentClienteLocalVistaBinding.inflate(layoutInflater)
        iniciar()

        return fbinding.root


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        configurarBottomSheet()

    }


    private fun configurarBottomSheet() {
        bottomSheet = fbinding.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight
    }

    //Codigo para que el rv se mire en dos columnas

//    val layoutManager = GridLayoutManager(activity, 2)
//    fbinding.rvProd.layoutManager = layoutManager
//    fbinding.rvProd.adapter = ProdAdapter(listaProd)



}