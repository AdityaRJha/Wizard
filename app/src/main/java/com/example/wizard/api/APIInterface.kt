package com.example.wizard.api

import com.example.wizard.dataClasses.Questions
import retrofit2.http.GET

interface APIInterface {

    @GET("api.php?amount=10&difficulty=hard&type=multiple")
    fun getDataHard(): Questions

    @GET("api.php?amount=10&difficulty=medium&type=multiple")
    fun getDataMedium(): Questions

    @GET("api.php?amount=10&difficulty=easy&type=multiple")
    fun getDataEasy(): Questions


}