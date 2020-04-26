package com.cs.http.http

import com.cs.http.http.intercept.CommonParamIntercept
import com.cs.http.http.intercept.DecryptResponseInterceptor
import com.cs.http.http.intercept.EncryptRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


class Http {

    companion object {


        const val BASE_URL = "https://api.github.com"
        const val BASE_URL2 = "http://221.181.129.89:20123"

        private val client = OkHttpClient.Builder()
            .addInterceptor(CommonParamIntercept())
            .addInterceptor(EncryptRequestInterceptor())
            .addInterceptor(DecryptResponseInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


        private val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL2)
            .build()


        @JvmStatic
        fun <T> create(clazz: Class<T>): T {
            return retrofit.create(clazz)
        }
    }
}