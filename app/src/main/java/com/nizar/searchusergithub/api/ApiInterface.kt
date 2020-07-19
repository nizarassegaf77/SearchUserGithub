package com.nizar.searchusergithub.api

import com.nizar.searchusergithub.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nizar Assegaf on 19,July,2020
 */

interface ApiInterface {
    @GET("search/users")
    fun getUsers(
        @Query("q") keyword: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<Users?>?
}
