package com.example.io

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket

/**
 * 在CMD命令行页面输入以下命令就可以连接成功
 * ttelnet localhost 8889
 */
object SocketTest {

    @JvmStatic
    fun main(args: Array<String>) {
        startServer()
    }


    private fun startServer() {

        val serverSocket = ServerSocket(8889)
        val socket = serverSocket.accept()  //这个方法是一个阻塞方法

        val reader = BufferedReader(InputStreamReader(socket.getInputStream())) //从客户端发来的内容
        val writer = BufferedWriter(OutputStreamWriter(socket.getOutputStream())) //服务端向客户端回复的内容

        while (true) {
            println("正在读取消息")
            val line = reader.readLine() //这里也是一个阻塞方法，如果对方没有消息输入，就阻塞在这里
            println("读取到消息 $line")
            if (line != null) {
                writer.write("我收到你的消息啦~ $line \n")
                writer.flush()
                println("回复消息")
            } else {
                println("跳出")
                break
            }
        }

        reader.close()
        writer.close()
    }

}