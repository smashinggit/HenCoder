package com.cs.http.http.intercept

import android.util.Log
import com.cs.http.http.encrypt.DES
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset

class DecryptResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if (!shouldDecrypt(request)) return chain.proceed(request)

        val response = chain.proceed(request)
        if (response.isSuccessful && response.body != null) {

            val source = response.body!!.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer

            val originalString = buffer.clone().readString(Charset.forName("UTF-8"))
            val decrypt = DES.decrypt(originalString, "asiainfo")
            Log.e("okhttp", "解密后：$decrypt")

            val decryptResponseBody = decrypt.toResponseBody(response.body!!.contentType())
            return response.newBuilder().body(decryptResponseBody).build()
        }
        return chain.proceed(request)
    }

    private fun shouldDecrypt(request: Request): Boolean {
        return request.url.encodedPath == "/edcaiom/front/ytj/ytjca!checkVersion"
    }
}