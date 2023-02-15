package com.markvtls.artstation.data.source.remote

import com.markvtls.artstation.data.dto.ImageResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiService {

        @GET("trending")
        suspend fun getLastTrendingGif(@Query("api_key") apiKey: String, @Query("limit") limit: Int): ImageResponseDto
}