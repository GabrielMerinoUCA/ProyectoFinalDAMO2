package com.fao.orderfy.presentacion.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fao.orderfy.databinding.FragmentVendedorLocalBinding
import com.fao.orderfy.datos.utils.TimePickerFragment
import java.sql.Time
import java.text.SimpleDateFormat


class VendedorLocalFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorLocalBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorLocalBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.etHoraCierre.setOnClickListener{showTimePickerDialog2()}
        fbinding.etHoraApertura.setOnClickListener{showTimePickerDialog()}
    }

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