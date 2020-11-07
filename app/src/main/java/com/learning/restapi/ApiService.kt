package com.learning.restapi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{user}/repos")
    fun getStarredRepos(@Path("user") userName: String): Single<List<Int>>

    // List<Int>
    fun getRepo(id: Int): Single<Api>
}