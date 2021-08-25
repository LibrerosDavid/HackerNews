package com.examen.hackernews.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.examen.hackernews.databinding.FragmentHackerNewsBinding
import com.examen.hackernews.model.Articulo
import com.examen.hackernews.modelData.Page
import com.examen.hackernews.ui.pageWeb.PageWebDialog
import com.examen.hackernews.util.Mensaje
import com.examen.hackernews.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class HackerNewsFragment : Fragment() {

    private lateinit var binding: FragmentHackerNewsBinding
    private val viewModel by viewModels<HackerNewsModel>()
    private val key = "delete_info"
    private lateinit var preferen:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHackerNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferen = PreferenceManager.getDefaultSharedPreferences(requireContext())

        viewModel.getArticulos.observe(viewLifecycleOwner,{


            when(it){
                is Resource.Loading -> {
                    Log.e("Resource.Loading","cargango")
                    binding!!.recaragDatos.isRefreshing = false
                    ocultaVista(true)
                }

                is Resource.Success -> {

                    Log.e("Resource.Success",it.data.size.toString())

                    showInfo(it.data)

                }

                is Resource.Fail -> {
                    showInfo(it.data)

                    Log.e("Resource.Fail",it.toString())
                }
            }
        })
        getInfo(false,"android")
        refreshData()
    }

    fun refreshData(){

        binding!!.recaragDatos.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
               getInfo(true,"android")
            }
        })
    }

    fun getInfo(recarga:Boolean,query: String){
        viewModel.setPage(
            Page(
                page = 0,
                recarga = recarga,
                query = query
            )
        )
    }

    fun ocultaVista(bandera:Boolean){
        if (bandera){
            binding.loadData.root.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }else{
            binding.loadData.root.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }


    fun sinDatos(){
        binding.recyclerView.visibility = View.GONE
        binding.loadData.root.visibility = View.GONE
        binding.sinDatos.root.visibility = View.VISIBLE
    }

    fun showInfo(list:List<Articulo>){
        if (list.isNotEmpty()){

            val linear = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )


            val infoBorrados = preferen.getString(key,null)
            val datosBase = list.toMutableList()

            //hackerNewsRecyclerViewAdapter.setData(ArrayList(datosBase))

/*           val hackerNewsRecyclerViewAdapter = HackerNewsRecyclerViewAdapter(ArrayList(list),requireContext(),)

            var datosBase = list.toMutableList()

            if (infoBorrados!=null){
                val id = infoBorrados.split(",")
                for (datoborrado:String in id){
                    datosBase.removeIf { datoborrado.toLong() == it.objectID }
                }
            }*/

            binding.recyclerView.layoutManager = linear
            val hackerNewsRecyclerViewAdapter = HackerNewsRecyclerViewAdapter(requireContext(),
                ArrayList(list)
            )
            binding.recyclerView.adapter = hackerNewsRecyclerViewAdapter


            hackerNewsRecyclerViewAdapter.setListener(object: HackerNewsRecyclerViewAdapter.HackerListener{
                override fun onClick(position:Int,tipo:Int) {

                    Log.e("position",position.toString())

                    if (tipo ==0 ){
                        if (datosBase[position].story_url!=null){
                            PageWebDialog.show(parentFragmentManager,datosBase[position].story_url)
                        }else{
                            Mensaje.showErrorDialogFragment(requireContext(),"No tiene url de historia")
                        }
                    }else{
                        val idDatos = preferen.getString(key,null)
                        val editor = preferen.edit()
                        if (idDatos!=null){
                            val id = idDatos.split(",")
                            val idborados = id.toMutableList()
                            idborados.add(list[position].objectID.toString())
                            editor.putString(key,idborados.joinToString(","))
                        }else{
                            editor.putString(key,list[position].objectID.toString())
                        }

                        editor.apply()
                    }

                }
            })

            ocultaVista(false)
        }else{
            sinDatos()
        }

    }

}