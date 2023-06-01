package com.fao.orderfy.presentacion.fragmentos

import android.content.DialogInterface
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.navArgs
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentAdministradorVistaSolicitudEspBinding
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelRegistro
import com.google.gson.JsonArray


class AdministradorVistaSolicitudEspFragment : Fragment() {
    private lateinit var fbinding: FragmentAdministradorVistaSolicitudEspBinding
    private val args by navArgs<AdministradorVistaSolicitudEspFragmentArgs>()
    private lateinit var viewModelRegistro: ViewModelRegistro

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        fbinding = FragmentAdministradorVistaSolicitudEspBinding.inflate(layoutInflater)
        cargarCampos()
        fbinding.btnAceptar.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Aceptar Solicitud")
            dialog.setMessage("¿Desea aceptar esta solicitud?")
            dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
                aceptarSolicitud()
            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(requireContext(), "Solicitud Aceptada", Toast.LENGTH_SHORT).show()
            })
            dialog.show()
        }
        fbinding.btnRechazar.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Rechazar Solicitud")
            dialog.setMessage("¿Desea rechazar esta solicitud?")
            dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
                rechazarSolicitud()
            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(requireContext(), "Solicitud Rechazada", Toast.LENGTH_SHORT).show()
            })
            dialog.show()
        }
        return fbinding.root
    }

    private fun cargarCampos() {

        val registro = cargarDatosSolicitud()
        if (registro.estado != 0){
            fbinding.btnAceptar.isVisible = false
            fbinding.btnRechazar.isVisible = false
        }
        val nombre = registro.nombre + " " + registro.apellido
        fbinding.tvSolicitante.text = nombre
        fbinding.tvNombreLocal.text = registro.nombreTienda
        fbinding.tvHoraApertura.text = registro.horaApertura.toString()
        fbinding.tvHoraCierre.text = registro.horaCierre.toString()
        fbinding.tvEstadoSolicitud.text = registro.estado.toString()

        if(registro.estado == 0){
            fbinding.tvEstadoSolicitud.text = "Pendiente"
        }
        if(registro.estado == 1){
            fbinding.tvEstadoSolicitud.text = "Aceptado"
        }
        if(registro.estado == 2){
            fbinding.tvEstadoSolicitud.text = "Rechazado"
        }
    }

    private fun cargarDatosSolicitud(): Registro {
        val registro = args.registro
        return registro
    }

    private fun aceptarSolicitud() {
        val registro = cargarDatosSolicitud()
        val viewModelRegistro =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelRegistro::class.java]
        viewModelRegistro.aceptarRegistro(object : MainListener{
            override fun onSuccess(response: JsonArray) {
                Toast.makeText(activity, "Solicitud Aceptada", Toast.LENGTH_LONG).show()
                cargarCampos()
                findNavController().popBackStack()


            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, registro)
    }

    private fun rechazarSolicitud() {
        val registro = cargarDatosSolicitud()
        val viewModelRegistro =
            ViewModelProvider(context as ViewModelStoreOwner)[ViewModelRegistro::class.java]
        viewModelRegistro.rechazarRegistro(object : MainListener{
            override fun onSuccess(response: JsonArray) {
                Toast.makeText(activity, "Solicitud Rechazada", Toast.LENGTH_LONG).show()
                cargarCampos()
                findNavController().popBackStack()


            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, registro)
    }

}
