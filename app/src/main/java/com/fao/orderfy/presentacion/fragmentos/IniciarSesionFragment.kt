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
import com.fao.orderfy.databinding.FragmentIniciarSesionBinding
import com.fao.orderfy.datos.Entidades.Cliente
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.ClienteActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelCliente
import com.google.gson.JsonArray


class IniciarSesionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var fbinding: FragmentIniciarSesionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentIniciarSesionBinding.inflate(layoutInflater)
        inicio()
        return fbinding.root
    }

    private fun inicio() {
        fbinding.tvRegistrar.setOnClickListener {
            Navigation.findNavController(fbinding.root)
                .navigate(R.id.action_iniciarSesionFragment_to_registroFragment)
        }
        fbinding.btnIniciarSesionC.setOnClickListener {
            if(fbinding.etUser.text.toString() == "" || fbinding.etPWD.text.toString() == ""){
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            }else{
                validarLogin()
            }

        }
    }

    private fun validarLogin() {
        var userName = fbinding.etUser.text.toString()
        var pwd = fbinding.etPWD.text.toString()
        var cliente = Cliente(0, "", "", userName, pwd)
        var viewModelCliente = ViewModelProvider(this)[ViewModelCliente::class.java]
        viewModelCliente.autenticarCliente(requireContext(), object : MainListener {
            override fun onSuccess(response: JsonArray) {
                val valor = validarJsonArray(response)
                if (valor) {
                    var intent = Intent(activity, ClienteActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity, "Usuario no encontrado", Toast.LENGTH_LONG)
                        .show()

                }

            }

            override fun onFailure(error: String) {
                Log.wtf("error", "error: "+error)
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG)
                    .show()
            }

        }, cliente)
    }

    fun validarJsonArray(jsonArray: JsonArray): Boolean {
        if (jsonArray.size() == 1) {
            val jsonObject = jsonArray.get(0).asJsonObject
            val valor = jsonObject.get("response").asBoolean
            return valor
        }
        return false
    }


}