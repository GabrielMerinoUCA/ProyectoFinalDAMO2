package com.fao.orderfy.presentacion.fragmentos

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.databinding.FragmentVendedorAgregarProdBinding


class VendedorAgregarProdFragment : Fragment() {
    private lateinit var fbinding: FragmentVendedorAgregarProdBinding
    private lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVendedorAgregarProdBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        cargarImagen()
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


}