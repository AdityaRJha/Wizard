package com.example.wizard.api

import com.example.wizard.dataClasses.Questions
import retrofit2.http.GET

interface APIInterface {

    @GET("amount=10&difficulty=hard&type=multiple")
    fun getDataHard(): Questions

    @GET("amount=10&difficulty=medium&type=multiple")
    fun getDataMedium(): Questions

    @GET("amount=10&difficulty=easy&type=multiple")
    fun getDataEasy(): Questions


}