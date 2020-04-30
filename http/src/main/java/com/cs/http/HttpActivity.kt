package com.cs.http

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cs.http.api.Api
import com.cs.http.http.Http
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        rxjava()

    }

    fun log(msg: String) {
        Log.e("tag", msg)
    }


    fun rxjava() {
        Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                log("Observable emit 1" + "\n")
                emitter.onNext(1)
                log("Observable emit 3" + "\n")
                emitter.onNext(3)
                log("Observable emit 5" + "\n")
                emitter.onNext(5)
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {

                var disposable: Disposable? = null

                override fun onComplete() {
                    log("onComplete")
                }

                override fun onSubscribe(d: Disposable?) {
                    log("onSubscribe")
                    disposable = d
                }

                override fun onNext(t: Int?) {
                    log("onNext $t")
                }

                override fun onError(e: Throwable?) {
                    log("onError ${e?.message}")
                }
            })
    }
}
