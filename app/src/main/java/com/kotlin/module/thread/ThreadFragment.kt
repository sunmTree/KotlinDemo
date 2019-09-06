package com.kotlin.module.thread

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.kotlin.demo.R
import kotlinx.android.synthetic.main.thread_fragment.*
import java.util.concurrent.CountDownLatch

private const val LOG_TAG = "Thread"

class ThreadFragment : Fragment() {
    private val mLock = Object()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.thread_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edit_time.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                countdownLatch(v.text.toString().toInt())
            }
            return@setOnEditorActionListener true
        }

    }


    private fun countdownLatch(time: Int) {
        val countDownLatch = CountDownLatch(1)
        synchronized(mLock) {
            Thread(Runnable {
                var times = time
                Log.d(LOG_TAG, "countdownLatch start $times")
                while (true) {
                    Thread.sleep(1000)
                    Log.d(LOG_TAG, "countdownLatch middle $times")
                    times--
                    if (times < 0) break
                }
                Log.d(LOG_TAG, "countdownLatch end $times")
                countDownLatch.countDown()
            },"Thread_CD_1").start()
        }
        countDownLatch.await()
    }

}