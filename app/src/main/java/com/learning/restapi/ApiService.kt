package com.learning.restapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class ApiEntries(
    val count: Int,
    val entries: List<Api>
)

interface ApiService {
    @GET("entries")
    fun listApis(@Query("category") category: String): Call<ApiEntries>
}