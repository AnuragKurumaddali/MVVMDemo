package com.kotlin.mvvmdemo.interfaces

import com.kotlin.mvvmdemo.models.Datum

interface ItemClickListener {
    fun OnItemClick(datum: Datum?)
}