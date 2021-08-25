package com.examen.hackernews.util

import android.content.Context
import android.util.Log
import android.widget.Toast

class Mensaje {

    companion object {
        fun showErrorDialogFragment(context: Context, error: String) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }
}