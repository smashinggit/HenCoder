package com.cs.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cs.http.api.Api
import com.cs.http.http.Http
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)


//        val call = Http.create(Api::class.java).getRepo("smashinggit")

        val call = Http.create(Api::class.java).checkUpdate("80", "371")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                log("onFailure ${t.message}")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                log("onResponse ${response.body()?.string()}")
            }
        })

    }

    fun log(msg: String) {
        Log.e("tag", msg)
    }
}
