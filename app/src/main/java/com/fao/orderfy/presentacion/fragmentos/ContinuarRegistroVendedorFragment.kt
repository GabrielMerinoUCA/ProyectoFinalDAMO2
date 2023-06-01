package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fao.orderfy.R
import com.fao.orderfy.datos.utils.TimePickerFragment
import com.fao.orderfy.databinding.FragmentContinuarRegistroVendedorBinding
import com.fao.orderfy.datos.Entidades.Registro
import com.fao.orderfy.datos.local.BD.BD
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelRegistro
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.sql.Time

class ContinuarRegistroVendedorFragment : Fragment() {
    private lateinit var fbinding: FragmentContinuarRegistroVendedorBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentContinuarRegistroVendedorBinding.inflate(layoutInflater)
        inicio()
        return fbinding.root
    }

    private fun inicio() {
        fbinding.etHoraCierre.setOnClickListener{showTimePickerDialog2()}
        fbinding.etHoraApertura.setOnClickListener{showTimePickerDialog()}
        fbinding.btnGuardar.setOnClickListener {
            if (validarEditText()){
                Toast.makeText(activity, "Todos los campos son requeridos", Toast.LENGTH_LONG)
                    .show()
            }else{
                registrarVendedor()
                Navigation.findNavController(fbinding.root).navigate(R.id.action_continuarRegistroVendedorFragment_to_seleccionUsuarioLoginFragment)
            }

        }
    }

    private fun registrarVendedor() {
        var bd = BD.getDatabase(requireContext()).DaoVendedor()
        var nombre = requireArguments().getString("nombre").toString()
        var apellido = requireArguments().getString("apellido").toString()
        var userName = requireArguments().getString("userName").toString()
        var pwd = requireArguments().getString("pwd").toString()
        var confirmPWD = requireArguments().getString("confirmPWD").toString()
        var nombreLocal = fbinding.etNombreLocal.text.toString()
        var horaApertura = convertStringToTime(fbinding.etHoraApertura.text.toString())
        var horaCierre = convertStringToTime(fbinding.etHoraCierre.text.toString())
        var registro = Registro(0, nombre, apellido, userName, nombreLocal, pwd, horaApertura!!, horaCierre!!, 0)
        var viewModelRegistro = ViewModelProvider(this)[ViewModelRegistro::class.java]
        viewModelRegistro.insertarRegistro(object : MainListener {
            override fun onSuccess(response: JsonArray) {

                Toast.makeText(activity, "Registro Ingresado Correctamente", Toast.LENGTH_LONG)
                    .show()


            }

            override fun onFailure(error: String) {
                activity?.runOnUiThread {
                    Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                }
            }

        }, registro)

    }

    private fun validarEditText(): Boolean{
        return fbinding.etHoraCierre.text.toString() == "" || fbinding.etHoraCierre.text.toString() == "" || fbinding.etNombreLocal.text.toString() == ""
    }

    /* Funciones de gestion de tiempo*/
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment {onTimeSelected(it)}
        timePicker.show(parentFragmentManager, "time")
    }

    private fun onTimeSelected(time:String){
        fbinding.etHoraApertura.setText("$time")
    }
    private fun onTimeSelected2(time:String){
        fbinding.etHoraCierre.setText("$time")
    }

    fun convertStringToTime(timeString: String): Time? {
        val format = SimpleDateFormat("HH:mm:ss")
        return try {
            val date = format.parse(timeString)
            Time(date.time)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showTimePickerDialog2() {
        val timePicker = TimePickerFragment {onTimeSelected2(it)}
        timePicker.show(parentFragmentManager, "time")
    }


}