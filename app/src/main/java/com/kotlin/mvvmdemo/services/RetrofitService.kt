package com.kotlin.mvvmdemo.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kotlin.mvvmdemo.interfaces.ApiInterface
import com.tchnte.codingtask.model.UserDataResponseDO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {


    companion object Factory {
        var gson = GsonBuilder().setLenient().create()
        fun create(): ApiInterface {
            Log.e("retrofit","create")

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }

        private const val BASE_URL = "https://gorest.co.in/"
    }

    fun loadUserData(pageCount: Int) : MutableLiveData<UserDataResponseDO>?{
        val  liveUserResponse : MutableLiveData<UserDataResponseDO> = MutableLiveData()

        val retrofitCall = create().getUserList(pageCount)

        retrofitCall.enqueue(object : Callback<UserDataResponseDO> {
            override fun onFailure(call: Call<UserDataResponseDO>, t: Throwable?) {
                Log.e("on Failure :", "retrofit error")
            }
            override fun onResponse(call: Call<UserDataResponseDO>, response: retrofit2.Response<UserDataResponseDO>) {
                liveUserResponse.value = response.body()
            }
        })

        return liveUserResponse
    }


}