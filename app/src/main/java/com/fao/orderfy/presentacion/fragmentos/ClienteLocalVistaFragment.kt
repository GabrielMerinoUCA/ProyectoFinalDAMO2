package com.fao.orderfy.presentacion.fragmentos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.fao.orderfy.R
import com.fao.orderfy.datos.Entidades.Producto
import com.fao.orderfy.databinding.FragmentClienteLocalVistaBinding
import com.fao.orderfy.datos.Entidades.Tienda
import com.fao.orderfy.datos.utils.ClienteVistaProdLocalListener
import com.fao.orderfy.datos.utils.MainListener
import com.fao.orderfy.presentacion.adapters.ClienteVistaProdLocalAdapter
import com.fao.orderfy.presentacion.viewmodel.ViewModelProducto
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonArray
import com.google.gson.JsonParser


class ClienteLocalVistaFragment : Fragment(), ClienteVistaProdLocalListener {
    private lateinit var fbinding: FragmentClienteLocalVistaBinding
    private lateinit var bottomSheet: LinearLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val args by navArgs<ClienteLocalVistaFragmentArgs>()
    var listaProd: MutableList<Producto> = mutableListOf()
    var producto: Producto? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentClienteLocalVistaBinding.inflate(layoutInflater)
        iniciar()

        return fbinding.root


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        configurarBottomSheet()
        var tienda = recuperarDatosTienda()
        fbinding.tvNombreLocal.text = tienda.nombre
        val bitmap: Bitmap? = BitmapFactory.decodeByteArray(tienda.logo, 0, tienda.logo.size)
        Glide.with(requireContext())
            .load(bitmap)
            .transform(
                CenterCrop(),
                RoundedCorners(20),
            )
            .into(fbinding.ivProdPortada)
        cargarDatosProducto(tienda)

    }

    private fun cargarDatosProducto(tienda: Tienda) {
        val activity = activity
        if (isAdded && activity != null) {
            listaProd.clear()
            val viewModelProducto =
                ViewModelProvider(context as ViewModelStoreOwner)[ViewModelProducto::class.java]
            viewModelProducto.consultarProducto(requireContext(), object : MainListener {
                override fun onSuccess(response: JsonArray) {
                    if (response != null) {
                        val jsonArrayString = response.toString()
                        val jsonArray = JsonParser.parseString(jsonArrayString).asJsonArray

                        for (element in jsonArray) {
                            // Obtener el objeto en la posición actual

                            val jsonObject = element.asJsonObject
                            val id = jsonObject.get("id")?.asString?.toInt()
                            val nombre = jsonObject.get("nombre")?.asString
                            val precio = jsonObject.get("precio")?.asFloat
                            val logoString = jsonObject.get("imagen")?.asString
                            val logoByteArray =
                                logoString?.let { Base64.decode(it, Base64.DEFAULT) }
                            val descripcion = jsonObject.get("descripcion")?.asString
                            val idTienda = jsonObject.get("idTienda")?.asString?.toInt()
                            val disponibilidad = jsonObject.get("disponibilidad")?.asBoolean
                            val tiempoEstimado = jsonObject.get("tiempoEstimado")?.asString?.toInt()

                            if (id != null && nombre != null && precio != null && logoByteArray != null && descripcion != null && idTienda != null && disponibilidad != null && tiempoEstimado != null) {
                                producto = Producto(
                                    id,
                                    nombre,
                                    precio,
                                    logoByteArray,
                                    descripcion,
                                    disponibilidad,
                                    tiempoEstimado,
                                    idTienda
                                )
                                listaProd.add(producto!!)
                            } else {
                                null
                            }

                        }
                        val layoutManager = GridLayoutManager(activity, 2)
                        fbinding.rvProd.layoutManager = layoutManager
                        fbinding.rvProd.adapter = ClienteVistaProdLocalAdapter(
                            listaProd,
                            requireContext(),
                            this@ClienteLocalVistaFragment
                        )


                    } else {
                        Toast.makeText(activity, "No hay producto", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(error: String) {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                    }
                }
            }, tienda.idTienda)
        }
    }

    private fun recuperarDatosTienda(): Tienda {
        val tienda = args.tienda
        return tienda
    }


    private fun configurarBottomSheet() {
        bottomSheet = fbinding.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight
    }

    override fun onSelectItemClick(producto: Producto) {
        val tienda = recuperarDatosTienda()
        val action = ClienteLocalVistaFragmentDirections.actionClienteLocalVistaFragmentToPlatilloFragment(tienda, producto)
        findNavController().navigate(action)



    }




}