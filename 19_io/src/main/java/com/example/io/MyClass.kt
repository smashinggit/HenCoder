package com.example.io

import java.io.*

object MyClass {

    @JvmStatic
    fun main(args: Array<String>) {

        val file = File("./19_io/read.txt")
        if (!file.exists()) {
            file.createNewFile()
        }

//        ioRead1(file)
//        io2(file)
//        io3(file)

        val fileCopy = File("./19_io/read_copy.txt")
//        fileCopy(file, fileCopy)

        fileCopy2(file, fileCopy)
    }

    private fun io1(file: File) {
        val outputStream = FileOutputStream(file)
        val data = byteArrayOf('a'.toByte(), 'b'.toByte())
        outputStream.write(data)
        outputStream.close()


        val inputStream = FileInputStream(file)
        while (inputStream.read() != -1) {
            val read = inputStream.read()
            println("读取内容: ${read.toByte().toChar()}")
        }
        inputStream.close()

    }

    private fun io2(file: File) {
        val outputStream = FileOutputStream(file)
        val outputStreamWriter = OutputStreamWriter(outputStream)
        outputStreamWriter.write("锄禾日当午")

        outputStreamWriter.close()
        outputStream.close()


        val inputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(inputStream)
        println("${inputStreamReader.readLines()}")

        inputStreamReader.close()
        inputStream.close()
    }

    /**
     * BufferedWriter、BufferedReader
     * 目的是减少I/O操作
     */
    private fun io3(file: File) {
        val outputStream = FileOutputStream(file)
        val outputStreamWriter = OutputStreamWriter(outputStream)
        val bufferedWriter = BufferedWriter(outputStreamWriter)
        bufferedWriter.write("汗滴禾下土")
//        bufferedWriter.flush()

        bufferedWriter.close()
        outputStreamWriter.close()
        outputStream.close()


        val inputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        println("${bufferedReader.readLine()}")

        bufferedReader.close()
        inputStreamReader.close()
        inputStream.close()
    }


    private fun fileCopy(source: File, fileCopy: File) {

        val inputStream = FileInputStream(source)
        val outputStream = FileOutputStream(fileCopy)

        val data = ByteArray(1024)

        while (true) {
            val readLength = inputStream.read(data)
            if (readLength != -1) {
                outputStream.write(data, 0, readLength)
            } else {
                break
            }
        }

        inputStream.close()
        outputStream.close()
    }

    fun fileCopy2(source: File, fileCopy: File) {
        val inputStream = FileInputStream(source)
        val outputStream = FileOutputStream(fileCopy)

        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream))

        bufferedReader.lineSequence().forEach {
            bufferedWriter.write(it)
        }

        bufferedReader.close()
        bufferedWriter.close()
        inputStream.close()
        outputStream.close()
    }
}
