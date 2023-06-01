package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.fao.orderfy.R
import com.fao.orderfy.databinding.ActivityClienteBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Vendedor

class ClienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClienteBinding
    private lateinit var sesionCliente: Cliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inicio()
    }

    private fun inicio() {
        configurarSlideBar()
        @Suppress("DEPRECATION")
        sesionCliente = intent.getParcelableExtra<Cliente>("SesionCliente")!!
        ClienteActivity.sesionClienteGlobal = sesionCliente
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

        binding.btnMiPerfil.setOnClickListener {
            val navController = binding.fragmentContainerView2.findNavController()
            navController.navigate(R.id.perfilFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE

        }
        binding.btnMisOrdenes.setOnClickListener {
            val navController = binding.fragmentContainerView2.findNavController()
            navController.navigate(R.id.misOrdenesFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
        binding.btnInicio.setOnClickListener {
            val navController = binding.fragmentContainerView2.findNavController()
            navController.navigate(R.id.clienteHomeFragment)
            binding.slidingPaneLayout.closePane()
            binding.slidingPaneLayout.visibility = View.INVISIBLE
        }
    }
    companion object{
        var sesionClienteGlobal: Cliente? = null
    }
}