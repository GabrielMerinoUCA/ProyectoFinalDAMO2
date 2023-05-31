package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import java.io.ByteArrayOutputStream


class VendedorEditarProdFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorEditarProdBinding
    private lateinit var viewModelProducto: ViewModelProducto
    private val args: VendedorEditarProdFragmentArgs by navArgs()
    private lateinit var launcher: ActivityResultLauncher<String>
    private lateinit var producto: Producto
    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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
                    Log.wtf("VendedorEditarProdFragment", error)
                }
            }, producto)
        }

        fbinding.btnEditarProducto.setOnClickListener {
            if(validarInputs()) {
                producto.nombre = fbinding.etNombreProd.text.toString().trim()
                producto.descripcion = fbinding.etDescipcion.text.toString().trim()
                producto.precio = fbinding.etPrecio.text.toString().trim().toFloat()
                producto.tiempoEstimado = fbinding.etTiempoEstimado.text.toString().trim().toInt()
                /* Validar que se haya ingresado una imagen */
                when (fbinding.ivLogo.drawable) {
                    is BitmapDrawable -> {
                        val bitmap: Bitmap = (fbinding.ivLogo.drawable as BitmapDrawable).bitmap
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray: ByteArray = stream.toByteArray()
                        producto.imagen = byteArray
                    }
                    is VectorDrawable -> {
                        Log.w("VECTOR","VECTOR")
                    }
                    else -> throw IllegalArgumentException("Unsupported drawable type")
                }
                viewModelProducto.editarProducto(object : MainListener{
                    override fun onSuccess(response: JsonArray) {
                        Log.wtf("VendedorEditarProdFragment", "${producto.disponibilidad}")
                        Toast.makeText(requireActivity(), "Guardado correctamente", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(fbinding.root).navigate(R.id.action_vendedorEditarProdFragment_to_vendedorMenuFragment)
                    }

                    override fun onFailure(error: String) {
                        Log.wtf("VendedorEditarProdFragment", error)
                    }
                }, producto)
            }
        }
    }

    private fun validarInputs(): Boolean {
        if(fbinding.etNombreProd.text.toString() == ""){
            Toast.makeText(requireActivity(), "Escriba el nombre del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(fbinding.etDescipcion.text.toString() == ""){
            Toast.makeText(requireActivity(), "Escriba la descripcion del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(fbinding.etPrecio.text.toString() == ""){
            Toast.makeText(requireActivity(), "Digite el precio del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(fbinding.etTiempoEstimado.text.toString() == ""){
            Toast.makeText(requireActivity(), "Escriba el tiempo estimado del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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