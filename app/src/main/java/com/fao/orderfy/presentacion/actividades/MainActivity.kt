package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fao.orderfy.databinding.ActivityMainBinding
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.repositorio.RepositorioAdministrador
import com.fao.orderfy.datos.utils.MainListener
import com.google.gson.JsonArray

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        iniciar()
        setContentView(binding.root)
    }

    private fun iniciar() {
        val repositorioAdministrador = RepositorioAdministrador()
        repositorioAdministrador.consultarAdministradorRemoto(object : MainListener{
            override fun onSuccess(response: JsonArray) {
                binding.tvPrueba.text = response.toString()
            }

            override fun onFailure(error: String) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
            }

        })
    }
}