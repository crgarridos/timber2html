package com.crgarridos.timber2html.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.crgarridos.timber2html.HtmlDebugTree
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.io.File


private const val SAMPLE_MESSAGE = "This is a sample message!"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val htmlTree = HtmlDebugTree(applicationContext)

        Timber.plant(Timber.DebugTree())
        Timber.plant(htmlTree)

        logSampleMessages()
        logsPathTextView.text = getString(R.string.logs_dir_path, htmlTree.logsDir.absoluteFile)

        openLogsDirButton.setOnClickListener {
            openFile(requireNotNull(htmlTree.logInstanceFile))
        }

    }

    private fun openFile(file: File) {
        val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "text/html")
        startActivity(intent)
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
