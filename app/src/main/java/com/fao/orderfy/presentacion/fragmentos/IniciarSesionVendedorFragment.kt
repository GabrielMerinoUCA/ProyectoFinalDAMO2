package com.fao.orderfy.presentacion.fragmentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentIniciarSesionVendedorBinding
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.AdminActivity
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelVendedor
import com.google.gson.JsonArray
import com.google.gson.JsonElement


class IniciarSesionVendedorFragment : Fragment() {
    private lateinit var fbinding: FragmentIniciarSesionVendedorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentIniciarSesionVendedorBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.tvRegistrar.setOnClickListener {
            Navigation.findNavController(fbinding.root)
                .navigate(R.id.action_iniciarSesionVendedorFragment_to_registroFragment)
        }
        fbinding.btnIniciarSesionC.setOnClickListener {
            if (fbinding.etUser.text.toString() == "" || fbinding.etPWD.text.toString() == "") {
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            } else {
                validarLogin()
            }
        }
    }


    private fun validarLogin() {

        var userName = fbinding.etUser.text.toString()
        var pwd = fbinding.etPWD.text.toString()
        var vendedor = Vendedor(0, 0, userName, pwd)

        var viewModelVendedor = ViewModelProvider(this)[ViewModelVendedor::class.java]
        viewModelVendedor.autenticarVendedor(object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val valor = validarJsonArray(response)
                if (valor.asBoolean) {
                    var intent = Intent(activity, VendedorActivity::class.java)
                    startActivity(intent)

                } else if (valor.asString == "admin-true") {
                    var intent = Intent(activity, AdminActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        activity,
                        "Usuario no encontrado",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            }

            override fun onFailure(error: String) {
                Log.wtf("error", "error: " + error)
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, vendedor)
    }

    fun validarJsonArray(jsonArray: JsonArray): JsonElement {
        val jsonObject = jsonArray.get(0).asJsonObject
        return jsonObject.get("response")

    }


}