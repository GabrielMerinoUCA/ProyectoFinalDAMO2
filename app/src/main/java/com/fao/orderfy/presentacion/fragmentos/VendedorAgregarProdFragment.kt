package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.fao.orderfy.databinding.FragmentVendedorAgregarProdBinding
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.actividades.VendedorActivity
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.google.gson.JsonArray
import java.io.ByteArrayOutputStream
import java.sql.Blob


class VendedorAgregarProdFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorAgregarProdBinding
    private lateinit var viewModelProducto: ViewModelProducto
    private lateinit var launcher: ActivityResultLauncher<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorAgregarProdBinding.inflate(layoutInflater)
        viewModelProducto = ViewModelProvider(this)[ViewModelProducto::class.java]
        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        cargarImagen()
        fbinding.btnGuardar.setOnClickListener {
            if (validarInputs()) {
                val producto = Producto(
                    0,
                    "",
                    0.0f,
                    ByteArray(0),
                    "",
                    true,
                    0,
                    VendedorActivity.sesionVendedorGlobal!!.idTienda
                )
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
                        Log.w("VECTOR", "VECTOR")
                    }

                    else -> throw IllegalArgumentException("Unsupported drawable type")
                }

                viewModelProducto.insertarProducto(object : MainListener {
                    override fun onSuccess(response: JsonArray) {
                        Log.wtf("JSONNNNNNNNNNNNNNN", response.toString())
                        Toast.makeText(requireActivity(), "Guardado correctamente", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(fbinding.root).navigate(R.id.action_vendedorAgregarProdFragment_to_vendedorMenuFragment)
                    }

                    override fun onFailure(error: String) {
                        Log.wtf("VendedorAgregarProdFragment", error)
                    }
                }, producto)
            }
        }
    }

    private fun validarInputs(): Boolean {
        if (fbinding.etNombreProd.text.toString() == "") {
            Toast.makeText(requireActivity(), "Escriba el nombre del producto", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (fbinding.etDescipcion.text.toString() == "") {
            Toast.makeText(
                requireActivity(),
                "Escriba la descripcion del producto",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (fbinding.etPrecio.text.toString() == "") {
            Toast.makeText(requireActivity(), "Digite el precio del producto", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (fbinding.etTiempoEstimado.text.toString() == "") {
            Toast.makeText(
                requireActivity(),
                "Escriba el tiempo estimado del producto",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun cargarImagen() {
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


}