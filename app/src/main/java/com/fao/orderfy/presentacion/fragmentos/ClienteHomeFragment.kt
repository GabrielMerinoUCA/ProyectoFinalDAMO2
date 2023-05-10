package com.fao.orderfy.presentacion.fragmentos

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
        fbinding.imageView5.setOnClickListener{
            Navigation.findNavController(fbinding.root).navigate(R.id.action_clienteHomeFragment_to_clienteLocalVistaFragment)
        }
       // Prueba recyclerview de manera horizontal
       fbinding.rvPopularProd.layoutManager
        var image :Drawable? = fbinding.root.context.getDrawable(R.drawable.hamburguesa)
        //image.setImageResource(R.drawable.hamburguesa)
        var prod = Producto(1,
        "manuela",
        "XD",
        130.00,
        true,
        LocalTime.now(),
        image!!,
        1)
        var prod1 = Producto(2,
            "manuela",
            "XD",
            130.00,
            true,
            LocalTime.now(),
            image,
            1)
        listaProd.add(prod)
        listaProd.add(prod1)
        fbinding.rvPopularProd.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        fbinding.rvPopularProd.adapter = PopularProdAdapter(listaProd)

        return fbinding.root
    }

}