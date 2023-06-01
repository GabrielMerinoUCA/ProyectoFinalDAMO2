package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.fao.orderfy.R
import com.fao.orderfy.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicio()
    }

    private fun inicio() {
        configurarSlideBar()
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

        binding.btnSolicitudes.setOnClickListener {
            val navController = binding.fragmentContainerView3.findNavController()
            navController.navigate(R.id.administradorVistaSolucitudesFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE

        }
        binding.btnUsuarios.setOnClickListener {
            val navController = binding.fragmentContainerView3.findNavController()
            navController.navigate(R.id.administradorVistaUsuarioFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnInicio.setOnClickListener {
            val navController = binding.fragmentContainerView3.findNavController()
            navController.navigate(R.id.administradorHomeFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnCerrarSesion.setOnClickListener {
            finish()
        }
    }
}