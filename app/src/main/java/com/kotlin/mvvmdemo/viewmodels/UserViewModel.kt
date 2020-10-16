package com.kotlin.mvvmdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.mvvmdemo.services.RetrofitService
import com.tchnte.codingtask.model.UserDataResponseDO

class UserViewModel : ViewModel() {

    private val userServiceResponse = RetrofitService()

    fun getUserData(pageCount: Int) : MutableLiveData<UserDataResponseDO>?{

        return userServiceResponse.loadUserData(pageCount)
    }
}