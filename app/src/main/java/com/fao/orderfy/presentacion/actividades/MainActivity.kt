package com.fao.orderfy.presentacion.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fao.orderfy.databinding.ActivityMainBinding
import com.fao.orderfy.datos.Entidades.Administrador
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.repositorio.RepositorioAdministrador
import com.fao.orderfy.datos.utils.MainListener
import com.google.gson.JsonArray

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /* ESTE ES EL CODIGO QUE TIENEN QUE IMPLEMENTAR
    private fun iniciar() {
        val admin = Administrador(1, "Gabriel", "Merino", "gab123", "123")
        val repositorioAdministrador = RepositorioAdministrador()
        repositorioAdministrador.editarAdministradorRemoto(object : MainListener{
            override fun onSuccess(response: JsonArray) {
                binding.tvPrueba.text = response.toString()
            }

            override fun onFailure(error: String) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
            }

        }, admin)
    }*/
}