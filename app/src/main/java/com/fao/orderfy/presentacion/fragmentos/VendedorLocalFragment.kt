package com.fao.orderfy.presentacion.fragmentos

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentVendedorLocalBinding
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.Entidades.Vendedor
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelTienda
import com.google.gson.JsonArray
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Locale
import com.fao.orderfy.datos.utils.TimePickerFragment


class VendedorLocalFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorLocalBinding
    private lateinit var viewModelTienda: ViewModelTienda
    private lateinit var sesionVendedor: Vendedor
    private lateinit var tiendaVendedor: Tienda
    private lateinit var launcher: ActivityResultLauncher<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorLocalBinding.inflate(layoutInflater)
        viewModelTienda = ViewModelProvider(this)[ViewModelTienda::class.java]
        sesionVendedor = VendedorActivity.sesionVendedorGlobal!!
        iniciar()
        return fbinding.root
    }




    private fun iniciar() {
        cargarImagen()
        cargarTienda()
        fbinding.btnCerrar.setOnClickListener {
            viewModelTienda.cambiarEstadoTienda(object : MainListener {
                override fun onSuccess(response: JsonArray) {
                    if (validarJsonArray(response)) {
                        Toast.makeText(
                            requireActivity(),
                            "Se han guardado los cambios",
                            Toast.LENGTH_SHORT
                        ).show()
                        cargarTienda()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "No se a podido cerrar el local",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(error: String) {
                    Toast.makeText(requireActivity(), "error", Toast.LENGTH_SHORT).show()
                    Log.wtf("ERROR AL CAMBIAR ESTADO DE TIENDA: ", error)
                }
            }, sesionVendedor.idTienda)
        }
        fbinding.etHoraCierre.setOnClickListener{showTimePickerDialog2()}
        fbinding.etHoraApertura.setOnClickListener{showTimePickerDialog()}
        fbinding.btnGuardar.setOnClickListener {
            fbinding.etHoraApertura.setText(tiendaVendedor.horaApertura.toString())
            fbinding.etHoraCierre.setText(tiendaVendedor.horaCierre.toString())
            fbinding.etNombreLocal.setText(tiendaVendedor.nombre)
        }
    }

    private fun cargarImagen(){
        launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                // Process the selected image here
                val selectedImageUri: Uri = result
                Glide.with(requireContext())
                    .load(selectedImageUri)
                    .override(fbinding.ivLogo.width, fbinding.ivLogo.height)
                    .transform(
                        CenterCrop(),
                        RoundedCorners(20),
                    )
                    .into(fbinding.ivLogo)
            }
        }

        fbinding.ivLogo.setOnClickListener {
            launcher.launch("image/*")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setBotonCerrar() {
        if (tiendaVendedor.estado) {
            fbinding.btnCerrar.text = "Cerrar"
            val color = ContextCompat.getColor(requireActivity(), R.color.rojo)
            fbinding.btnCerrar.backgroundTintList = ColorStateList.valueOf(color)
            fbinding.btnCerrar.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.white)))

        } else {
            fbinding.btnCerrar.text = "Abrir"
            val color = ContextCompat.getColor(requireActivity(), R.color.azul)
            fbinding.btnCerrar.backgroundTintList = ColorStateList.valueOf(color)
            fbinding.btnCerrar.setTextColor(ColorStateList.valueOf(Color.parseColor("#175676")))
        }
    }

    private fun cargarTienda() {
        viewModelTienda.consultarTienda(requireContext(), object : MainListener {
            override fun onSuccess(response: JsonArray) {
                var tienda: Tienda?
                tienda = obtenerTiendas(response)
                if (tienda != null) {
                    Log.wtf("TIENDA: ", "${tienda.nombre} y ${tienda.estado}")
                    tiendaVendedor = tienda
                    setBotonCerrar()
                } else {
                    tiendaVendedor = Tienda(0, "", ByteArray(0), Time(1), Time(1), false)
                    Toast.makeText(
                        requireActivity(),
                        "Error al cargar la tienda...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(error: String) {
                Log.wtf("Vendedor Local Framgent:", error)
            }
        })
    }

    private fun obtenerTiendas(response: JsonArray): Tienda? {
        val formatoHora = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val length = response.size()
        var i = 0
        do {
            if (length != 0) {
                val tiendaJsonObject = response.get(i).asJsonObject

                val id = tiendaJsonObject.get("id").asInt

                if (id == sesionVendedor.idTienda) {
                    val nombre = tiendaJsonObject.get("nombre").asString

                    val horaAperturaString = tiendaJsonObject.get("horaApertura").asString
                    val horaAperturaTime = Time(formatoHora.parse(horaAperturaString).time)

                    val horaCierreString = tiendaJsonObject.get("horaCierre").asString
                    val horaCierreTime = Time(formatoHora.parse(horaCierreString).time)

                    val logoString = tiendaJsonObject.get("logo").asString
                    val logoByteArray = Base64.decode(logoString, Base64.DEFAULT)

                    val estadoInt = tiendaJsonObject.get("estado").asInt
                    var estado: Boolean = false
                    if (estadoInt == 1) {
                        estado = true
                    } else if (estadoInt == 0) {
                        estado = false
                    }

                    return Tienda(
                        id, nombre, logoByteArray, horaAperturaTime, horaCierreTime, estado
                    )
                }
            }
            i++
        } while (i < length)
        return null
    }

    private fun validarJsonArray(jsonArray: JsonArray): Boolean {
        if (jsonArray.size() == 1) {
            val jsonObject = jsonArray.get(0).asJsonObject
            val valor = jsonObject.get("response").asBoolean
            return valor
        }
        return false
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