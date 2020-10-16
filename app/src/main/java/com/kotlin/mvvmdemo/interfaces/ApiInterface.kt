package com.kotlin.mvvmdemo.interfaces

import com.tchnte.codingtask.model.UserDataResponseDO
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("public-api/users")
    fun getUserList(@Query("page") page: Int): Call<UserDataResponseDO>
}