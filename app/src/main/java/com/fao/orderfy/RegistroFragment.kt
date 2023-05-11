package com.fao.orderfy

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.fao.orderfy.databinding.FragmentRegistroBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentRegistroBinding
    private var selectedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentRegistroBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        validarBotonSelecionado()
        fbinding.tvIniciarSesionC.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.action_registroFragment_to_iniciarSesionFragment)
        }
    }

    private fun validarBotonSelecionado() {
        fbinding.btnCliente.setOnClickListener {
            clearButtonSelection()
            selectedButton = fbinding.btnCliente
            fbinding.btnCliente.setBackgroundColor(Color.parseColor("#CCE6F4"))
            fbinding.btnVendedor.setBackgroundColor(Color.parseColor("#D9D9D9"))
        }
        fbinding.btnVendedor.setOnClickListener {
            clearButtonSelection()
            selectedButton = fbinding.btnVendedor
            fbinding.btnVendedor.setBackgroundColor(Color.parseColor("#CCE6F4"))
            fbinding.btnCliente.setBackgroundColor(Color.parseColor("#D9D9D9"))
        }
        fbinding.btnContinuar.setOnClickListener {
            if (selectedButton == fbinding.btnCliente) {
                // Navega al fragmento para el botón 1
                Navigation.findNavController(fbinding.root).navigate(R.id.action_registroFragment_to_crearCuentaFragment)

            } else if (selectedButton == fbinding.btnVendedor) {
                Navigation.findNavController(fbinding.root).navigate(R.id.action_registroFragment_to_registroVendedorFragment)

            } else {
                // Ningún botón está seleccionado
                Toast.makeText(activity, "Seleccione un botón primero", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearButtonSelection() {
        // Deselecciona cualquier botón que se haya seleccionado previamente
        if (selectedButton != null) {
            selectedButton?.isSelected = false
            selectedButton = null
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}