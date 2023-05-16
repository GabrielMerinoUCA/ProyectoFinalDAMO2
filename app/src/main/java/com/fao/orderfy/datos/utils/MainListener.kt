package com.fao.orderfy.datos.utils

import com.google.gson.JsonArray

interface MainListener {
    fun onSuccess(response: JsonArray)
    fun onFailure(error: String)
}