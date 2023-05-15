package com.fao.orderfy.presentacion.actividades

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.fao.orderfy.databinding.ActivityMainBinding
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.api.RetrofitService
import com.fao.orderfy.datos.dao.DaoTienda
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.internal.commonAsUtf8ToByteArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.sql.Time

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tiendas: ArrayList<Tienda> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}