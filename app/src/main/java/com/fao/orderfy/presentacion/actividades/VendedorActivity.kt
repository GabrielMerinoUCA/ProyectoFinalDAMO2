package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.fao.orderfy.R
import com.fao.orderfy.databinding.ActivityVendedorBinding
import com.fao.orderfy.datos.Entidades.Vendedor

class VendedorActivity : AppCompatActivity() {
    companion object{
        var sesionVendedorGlobal: Vendedor? = null
    }

    private lateinit var binding: ActivityVendedorBinding
    private lateinit var sesionVendedor: Vendedor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicio()
    }

    private fun inicio() {
        configurarSlideBar()
        sesionVendedor = intent.getParcelableExtra<Vendedor>("SesionVendedor")!!
        Log.wtf("VendedorActivity", "SESION VENDEDOR: ${sesionVendedor.nombreUsuario}")
        sesionVendedorGlobal = sesionVendedor
    }

    private fun configurarSlideBar() {
        binding.btnAbrirSlide.setOnClickListener {
            val layoutParams = binding.slidingPaneLayout.layoutParams as ViewGroup.MarginLayoutParams
            val screenWidth = resources.displayMetrics.widthPixels
            val halfScreenOffset = 0.8f
            val halfScreenWidth = (screenWidth * halfScreenOffset).toInt()

            layoutParams.width = halfScreenWidth
            binding.slidingPaneLayout.openPane()
            binding.slidingPaneLayout.layoutParams = layoutParams
            binding.slidingPaneLayout.requestLayout()
            if (binding.slidingPaneLayout.isVisible ) {
                binding.slidingPaneLayout.closePane()
                binding.slidingPaneLayout.visibility = View.INVISIBLE
            }else {
                binding.slidingPaneLayout.visibility = View.VISIBLE
                binding.slidingPaneLayout.openPane()
            }
        }

        binding.btnOrdenesEnCurso.setOnClickListener {
            val navController = binding.fragmentContainerView4.findNavController()
            navController.navigate(R.id.vendedorHomeFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnOrdenesRecientes.setOnClickListener {
            val navController = binding.fragmentContainerView4.findNavController()
            navController.navigate(R.id.vendedorOrdenesFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnMenu.setOnClickListener {
            val navController = binding.fragmentContainerView4.findNavController()
            navController.navigate(R.id.vendedorMenuFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnMiLocal.setOnClickListener {
            val navController = binding.fragmentContainerView4.findNavController()
            navController.navigate(R.id.vendedorLocalFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnCerrarSesion.setOnClickListener {
            finish()
        }
    }
}