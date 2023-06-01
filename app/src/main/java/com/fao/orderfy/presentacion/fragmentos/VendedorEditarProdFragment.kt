package com.fao.orderfy.presentacion.fragmentos

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.databinding.FragmentVendedorEditarProdBinding
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.google.gson.JsonArray


class VendedorEditarProdFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorEditarProdBinding
    private lateinit var viewModelProducto: ViewModelProducto
    private val args: VendedorEditarProdFragmentArgs by navArgs()
    private lateinit var launcher: ActivityResultLauncher<String>
    private lateinit var producto: Producto
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorEditarProdBinding.inflate(layoutInflater)
        viewModelProducto = ViewModelProvider(this)[ViewModelProducto::class.java]
        producto = args.producto
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        cargarImagen()
        fbinding.etNombreProd.setText(producto.nombre)
        fbinding.etDescipcion.setText(producto.descripcion)
        fbinding.etPrecio.setText(producto.precio.toString())
        fbinding.etTiempoEstimado.setText(producto.tiempoEstimado.toString())
        cambiarBtnEditar()

        /*Este cambia el estado de disponibilidad */
        fbinding.btnEditar.setOnClickListener {
            viewModelProducto.cambiarDisponibilidadProducto(object : MainListener{
                override fun onSuccess(response: JsonArray) {
                    if(validarJsonArray(response)){
                        Toast.makeText(requireActivity(), "Guardado exitosamente", Toast.LENGTH_SHORT).show()
                        if(producto.disponibilidad){
                            producto.disponibilidad = false
                        } else if(!producto.disponibilidad){
                            producto.disponibilidad = true
                        }
                        cambiarBtnEditar()
                    }else{
                        Toast.makeText(requireActivity(), "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(error: String) {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                    Log.wtf("Vendedor Editar Prod Frament: ", error)
                }
            }, producto)
        }

        fbinding.btnEliminarProducto.setOnClickListener {
            viewModelProducto.eliminarProducto(object : MainListener{
                override fun onSuccess(response: JsonArray) {
                    if(validarJsonArray(response)){
                        Toast.makeText(requireActivity(), "Eliminado exitosamente", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(fbinding.root).navigate(R.id.action_vendedorEditarProdFragment_to_vendedorMenuFragment)
                    }
                    else{
                        Toast.makeText(requireActivity(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(error: String) {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                }

            }, producto)
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

    private fun cambiarBtnEditar() {
        if(producto.disponibilidad) {
            fbinding.btnEditar.text = "Disponible"
        }else{
            fbinding.btnEditar.text = "Agotado"
        }
    }

    private fun validarJsonArray(jsonArray: JsonArray): Boolean {
        if (jsonArray.size() == 1) {
            val jsonObject = jsonArray.get(0).asJsonObject
            val valor = jsonObject.get("response").asBoolean
            return valor
        }
        return false
    }



}