package com.crgarridos.timber2html.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crgarridos.timber2html.HtmlDebugTree
import timber.log.Timber


private const val SAMPLE_MESSAGE = "This is a sample message!"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val htmlTree = HtmlDebugTree(applicationContext)

        Timber.plant(Timber.DebugTree())
        Timber.plant(htmlTree)

        logSampleMessages()
    }

    private fun logSampleMessages() {
        Timber.v(SAMPLE_MESSAGE)
        Timber.d(SAMPLE_MESSAGE)
        Timber.i(SAMPLE_MESSAGE)
        Timber.w(SAMPLE_MESSAGE)
        Timber.e(SAMPLE_MESSAGE)
        Timber.wtf(SAMPLE_MESSAGE)

        Timber.v(Exception(SAMPLE_MESSAGE))
        Timber.d(Exception(SAMPLE_MESSAGE))
        Timber.i(Exception(SAMPLE_MESSAGE))
        Timber.w(Exception(SAMPLE_MESSAGE))
        Timber.e(Exception(SAMPLE_MESSAGE))
        Timber.wtf(Exception(SAMPLE_MESSAGE))
    }
}
