package com.example.io.okio

import okio.*
import java.io.File

object OkioMain {


    @JvmStatic
    fun main(args: Array<String>) {

        val file = File("./19_io/read.txt")
        if (!file.exists()) {
            file.createNewFile()
        }

        val pic = File("./19_io/pic2.png")

//        io1(file)
        copy(pic)

    }

    private fun copy(pic: File) {
        val source = pic.source()
        val buffer = source.buffer()
        source.c
        while (buffer.read())


    }

    fun io1(file: File) {

//        val source = file.source()
//        val buffer = Buffer()
//        source.read(buffer, 1024)
//
//        println(buffer.readUtf8())
//        source.close()

        file.source().buffer().readUtf8() //更便捷的写法


        val sink = file.sink(true)
        val sinkBuffer = Buffer()
        sinkBuffer.writeUtf8("\n 谁知盘中餐")
        sink.write(sinkBuffer, sinkBuffer.size)
        sink.close()

    }

}