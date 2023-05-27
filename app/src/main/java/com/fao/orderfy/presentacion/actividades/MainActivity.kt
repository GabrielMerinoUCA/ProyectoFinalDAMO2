package com.fao.orderfy.presentacion.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fao.orderfy.databinding.ActivityMainBinding
import com.fao.orderfy.datos.Entidades.Administrador
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.local.dao.DaoCliente
import com.fao.orderfy.datos.repositorio.RepositorioAdministrador
import com.fao.orderfy.datos.repositorio.RepositorioCliente
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.fao.orderfy.presentacion.viewmodel.ViewModelVendedor
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var bd: BD


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