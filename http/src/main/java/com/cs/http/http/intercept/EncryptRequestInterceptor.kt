package com.cs.http.http.intercept

import android.util.Log
import com.cs.http.http.encrypt.AseEcb
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder

class EncryptRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if (!shouldEncrypt(request)) return chain.proceed(request)  //不加密

        val body = request.body
        if (body is FormBody) {
            val jsonObject = JsonObject()

            for (i in 0 until body.size) {
                jsonObject.addProperty(body.encodedName(i), body.encodedValue(i))
            }

            Log.e("okhttp", "加密前：$jsonObject")

            val encryptContent = getEncryptContent(request, jsonObject.toString())
            Log.e("okhttp", "加密后：$encryptContent")

            val encryptRequestBody =
                encryptContent.toRequestBody("text/plain; charset=UTF-8".toMediaTypeOrNull())

            return chain.proceed(
                request.newBuilder()
                    .method(request.method, encryptRequestBody)
                    .build()
            )

        }

        return chain.proceed(request)
    }

    private fun getEncryptContent(request: Request, content: String): String {
        return URLEncoder.encode(AseEcb.encrypt(content, "^YHN*IK<#EDC@#/$"), "UTF-8")
    }

    private fun shouldEncrypt(request: Request): Boolean {
        return request.url.encodedPath == "/edcaiom/front/ytj/ytjca!checkVersion"
    }
}