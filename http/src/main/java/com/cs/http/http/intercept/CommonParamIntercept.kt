package com.cs.http.http.intercept

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

class CommonParamIntercept : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val body = request.body

        if (body is FormBody) {
            val newRequestBuilder = request.newBuilder()
            val newBodyBuilder = FormBody.Builder()

            for (i in 0 until body.size) {
                newBodyBuilder.addEncoded(body.encodedName(i), body.encodedValue(i))
            }
            newBodyBuilder.add("name1", "value1")
            newBodyBuilder.add("name2", "value2")

            newRequestBuilder.method(request.method, newBodyBuilder.build())

            return chain.proceed(newRequestBuilder.build())
        }

        return chain.proceed(request)
    }
}