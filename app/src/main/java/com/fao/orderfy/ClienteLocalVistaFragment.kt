package com.fao.orderfy

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fao.orderfy.Entidades.Producto
import com.fao.orderfy.Recyclerview.PopularProdAdapter
import com.fao.orderfy.Recyclerview.ProdAdapter
import com.fao.orderfy.databinding.FragmentClienteLocalVistaBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [ClienteLocalVistaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteLocalVistaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentClienteLocalVistaBinding
    private lateinit var bottomSheet: LinearLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    var listaProd: MutableList<Producto> = mutableListOf()
    lateinit var adatador: ProdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentClienteLocalVistaBinding.inflate(layoutInflater)
        bottomSheet = fbinding.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.peekHeight
        var image : Drawable? = fbinding.root.context.getDrawable(R.drawable.hamburguesa)
        //image.setImageResource(R.drawable.hamburguesa)
        var prod = Producto(1,
            "manuela",
            "XD",
            130.00,
            true,
            LocalTime.now(),
            image!!,
            1)
        var prod1 = Producto(2,
            "manuela",
            "XD",
            130.00,
            true,
            LocalTime.now(),
            image,
            1)
        listaProd.add(prod)
        listaProd.add(prod1)
        var prod3 = Producto(1,
            "manuela",
            "XD",
            130.00,
            true,
            LocalTime.now(),
            image!!,
            1)
        var prod4 = Producto(2,
            "manuela",
            "XD",
            130.00,
            true,
            LocalTime.now(),
            image,
            1)
        listaProd.add(prod3)
        listaProd.add(prod4)
        val layoutManager = GridLayoutManager(activity, 2)


        fbinding.rvProd.layoutManager = layoutManager
        fbinding.rvProd.adapter = ProdAdapter(listaProd)


        return fbinding.root


    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClienteLocalVistaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClienteLocalVistaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}