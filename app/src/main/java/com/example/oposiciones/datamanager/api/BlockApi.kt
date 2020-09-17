package com.example.oposiciones.datamanager.api

import com.example.oposiciones.data.Block
import retrofit2.http.GET

interface BlockApi {

    @GET("blocks/")
    suspend fun getBlocks(): List<Block>

}
