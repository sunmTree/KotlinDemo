package com.kotlin.module.kotlin_demo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kotlin.demo.R
import java.util.concurrent.TimeUnit

class KotlinDemoFragment : Fragment() {
    private var mFilterTimeCountDown: CountDownFilterTask = CountDownFilterTask(HandleTimeCountDown())
    private var mHandler: Handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_java_simple, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val task10 = FilterTask(timeout = TimeUnit.SECONDS.toMillis(10), runnable = Runnable {
            Toast.makeText(context, "task10 running", Toast.LENGTH_SHORT).show()
        })

        val task5 = FilterTask(timeout = TimeUnit.SECONDS.toMillis(5), runnable = Runnable {
            Toast.makeText(context, "task5 running", Toast.LENGTH_SHORT).show()
        })

        val task2 = FilterTask(timeout = TimeUnit.SECONDS.toMillis(2), runnable = Runnable {
            Toast.makeText(context, "task2 running", Toast.LENGTH_SHORT).show()
        })

        mFilterTimeCountDown.addFilterTask(task5)
        mFilterTimeCountDown.addFilterTask(task10)

        mHandler.postDelayed({ mFilterTimeCountDown.addFilterTask(task2) }, TimeUnit.SECONDS.toMillis(5))

        mFilterTimeCountDown.startCountDown()
        mHandler.postDelayed({
            mFilterTimeCountDown.stopCountDown()
            Toast.makeText(context, "Finish count down", Toast.LENGTH_SHORT).show()
        }, TimeUnit.SECONDS.toMillis(20))
    }

}

private const val LOG_TAG = "CountDownFilter"
class CountDownFilterTask(private val countDown: TimeCountDown) : TimeCallback {
    private val mFilterTasks = mutableListOf<FilterTask>()
    private var mStartTime: Long = countDown.getCurrentTime()
    private var isStarted: Boolean = false

    init {
        countDown.registerTimeChange(this)
    }

    fun startCountDown() {
        mFilterTasks.forEach { it.startTime = mStartTime }
        countDown.startTime()
        isStarted = true
        Log.d(LOG_TAG, "start count down")
    }

    fun stopCountDown() {
        countDown.stop()
        Log.d(LOG_TAG, "stop count down")
    }

    fun addFilterTask(task: FilterTask) {
        if (isStarted) {
            task.startTime = countDown.getCurrentTime()
        }
        mFilterTasks.add(task)
    }

    override fun onTimeChange(time: Long) {
        Log.d(LOG_TAG, "on time change $time")
        mFilterTasks.forEach {
            if (time - it.startTime == it.timeout) {
                it.runnable.run()
            }
        }
    }

}

class FilterTask(var startTime: Long = 0L, var timeout: Long, var runnable: Runnable)

interface TimeCountDown {
    fun startTime()
    fun stop()
    fun registerTimeChange(callback: TimeCallback)
    fun getCurrentTime(): Long
}

interface TimeCallback {
    fun onTimeChange(time: Long)
}

class HandleTimeCountDown : TimeCountDown, Handler.Callback {
    private val countDownTask = 1
    private val mHandler: Handler = Handler(Looper.getMainLooper(), this)
    private var mCallback: TimeCallback? = null
    private var mStartTime: Long = 0L
    private var isStop = false

    override fun startTime() {
        mHandler.sendEmptyMessage(countDownTask)
    }

    override fun stop() {
        isStop = true
        mHandler.removeCallbacksAndMessages(null)
    }

    override fun registerTimeChange(callback: TimeCallback) {
        mCallback = callback
    }

    override fun getCurrentTime(): Long = mStartTime

    override fun handleMessage(msg: Message?): Boolean {
        if (msg?.what == countDownTask && !isStop) {
            mCallback?.onTimeChange(mStartTime)
            mHandler.postDelayed({
                mStartTime += TimeUnit.SECONDS.toMillis(1)
                mHandler.sendEmptyMessage(countDownTask)
            }, TimeUnit.SECONDS.toMillis(1))
        }

        return true
    }
}