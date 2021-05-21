package com.example.tast1.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class FetchDataRepository(context: Context) {

    private val volleyQueue : RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun fetchData(url : String, onSuccess : (String) -> Unit, onFailure : () -> Unit){
        val stringRequest : StringRequest = StringRequest(Request.Method.GET, url, { response ->
            onSuccess(response)
        } , {
            onFailure()
        })

        volleyQueue.add(stringRequest)
    }
}