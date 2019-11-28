package com.kotlin.module.kotlin_demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KotlinObserver {

    fun coroutineDemo() = runBlocking {
        launch(Dispatchers.IO){
            println("beijing ${Thread.currentThread()}")
        }
    }

}