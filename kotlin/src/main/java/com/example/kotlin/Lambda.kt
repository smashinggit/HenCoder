package com.example.kotlin

class Lambda {


    fun main() {

        val addFunction = { a: Int, b: Int ->
            a + b
        }

        operateTwoInt(1, 2, addFunction)
        operateTwoInt(2, 4) { a, b ->
            a + b
        }

        val aObject = ::operateTwoInt

    }


    fun operateTwoInt(a: Int, b: Int, function: (Int, Int) -> Int): Int {
        return function(a, b)
    }


    fun b(param: Int): String {
        return param.toString()
    }


}