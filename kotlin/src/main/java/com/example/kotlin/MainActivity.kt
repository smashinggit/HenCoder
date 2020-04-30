package com.example.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()
            ioCode2()
            uiCode2()
            ioCode3()
            uiCode3()
        }
    }

    private suspend fun ioCode1() {
        withContext(Dispatchers.IO) {
            print("ioCode1  in  ${Thread.currentThread().name}")
        }
    }

    private suspend fun ioCode2() {
        withContext(Dispatchers.IO) {
            delay(3000)
            print("ioCode2  in  ${Thread.currentThread().name}")
        }
    }

    private suspend fun ioCode3() {
        withContext(Dispatchers.IO) {
            print("ioCode3  in  ${Thread.currentThread().name}")
        }
    }

    private fun uiCode1() {
        print("uiCode1  in  ${Thread.currentThread().name}")
    }

    private fun uiCode2() {
        print("uiCode2  in  ${Thread.currentThread().name}")
    }

    private fun uiCode3() {
        print("uiCode3  in  ${Thread.currentThread().name}")
    }

    private fun print(msg: String) {
        Log.e("tag", msg)
    }
}
