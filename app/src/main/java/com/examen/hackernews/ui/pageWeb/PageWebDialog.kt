package com.examen.hackernews.ui.pageWeb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.examen.hackernews.R
import com.examen.hackernews.databinding.WebBinding

class PageWebDialog: DialogFragment() {
    private var binding: WebBinding? = null
    private var url:String? = null

    companion object {

        fun show(fragmentManager: FragmentManager, url:String?): PageWebDialog {
            val dialog = PageWebDialog()
            val datos = Bundle()
            Log.e("url",url.toString())
            datos.putString("url",url)
            dialog.arguments = datos
            dialog.show(fragmentManager, "web")
            return dialog
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
        if (arguments!=null){
            url = arguments?.getString("url")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WebBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.close.setOnClickListener {
            this.dismiss()
        }
        if(url!=null){
            binding!!.web.loadUrl(url!!)
            val webSettings: WebSettings = binding!!.web.settings;
            webSettings.javaScriptEnabled = true
            binding!!.web.webViewClient = WebViewClient()
        }else{
            this.dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}