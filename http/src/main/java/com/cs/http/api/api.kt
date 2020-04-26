package com.cs.http.api

import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("/users/{user}/repos")
    fun getRepo(@Path("user") user: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/edcaiom/front/ytj/ytjca!checkVersion")
    fun checkUpdate(@Field("v") version: String, @Field("proCode") province: String): Call<ResponseBody>

    @POST("")
    fun post1(@Body msg: String): Call<ResponseBody>

    @POST("")
    fun post2(@Body body: RequestBody): Call<ResponseBody>

    @FormUrlEncoded
    @POST("")
    fun post3(@Field("name") name: String, @FieldMap param: Map<String, String>): Call<ResponseBody>

    @Multipart
    @POST("")
    fun post4(@Part("photo") photo: RequestBody, @Part("des") des: String)

}