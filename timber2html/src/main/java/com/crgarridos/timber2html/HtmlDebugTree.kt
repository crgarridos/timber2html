package com.crgarridos.timber2html

import android.content.Context
import android.os.Environment
import android.util.Log
import com.crgarridos.timber2html.utils.format
import timber.log.Timber

import java.io.File
import java.io.IOException
import java.util.Date

// Based on darcula theme and material colors
// https://stackoverflow.com/questions/39993867/android-studio-logcat-colors-best-practice
private const val DARCULA_BACKGROUND_COLOR = "#3C3F41"
private const val LOG_VERBOSE_TEXT_COLOR = "#B0B0B0"
private const val LOG_DEBUG_TEXT_COLOR = "#2196F3"
private const val LOG_INFO_TEXT_COLOR = "#4CAF50"
private const val LOG_WARN_TEXT_COLOR = "#FFC107"
private const val LOG_ERROR_TEXT_COLOR = "#F44336"
private const val LOG_ASSERT_TEXT_COLOR = "#B87BD5"

private const val LOGS_FOLDER_NAME = "Logs"

class HtmlDebugTree(context: Context) : Timber.DebugTree() {

    val logsDir = File(context.applicationContext.getExternalFilesDir(null), LOGS_FOLDER_NAME)
    val logInstanceFile = createLogFile(Date())

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        try {
            val logTimeStamp = Date().format("E MMM dd yyyy 'at' HH:mm:ss:SSS")
            logInstanceFile?.log(priority, """
                <b class="date">$logTimeStamp</b><b>$tag</b> - $message
            """.trimIndent())
        } catch (e: IOException) {
            Timber.e(e, "FileLoggingTree: Error while logging into file")
        }
    }

    private fun createLogFile(date: Date): File? {
        try {
            checkExternalStorageAvailable()
            checkLogDirExistence()

            val timestamp = date.format("E MMM dd yyyy HH:mm:ss")
            val fileName = "$timestamp.html"
            return File(logsDir, fileName).withStyle()
        } catch (ex: IllegalStateException) {
            Timber.e(ex, "Unable to create log file")
        }
        return null
    }

    private fun checkLogDirExistence() {
        check(logsDir.exists() || logsDir.mkdirs()) {
            "$logsDir doesn't exist and can't be created"
        }
    }

    private fun checkExternalStorageAvailable() {
        val externalStorageState = Environment.getExternalStorageState()
        check(Environment.MEDIA_MOUNTED == externalStorageState) {
            "Check if the external storage is mounted (state: $externalStorageState)"
        }
    }

    private fun File.log(priority: Int, text: String) {
        appendText("""
            <p style="color: ${getPriorityTextColor(priority)};">$text</p>
        """.trimIndent())
    }

    private fun File.withStyle(): File = apply {
        appendText("""<head><style type="text/css">
            body {
                background: $DARCULA_BACKGROUND_COLOR;
                font-family: monospace;
                margin: 0; padding; 0;
            }
            p {
                border-top: 1px solid #B0B0B0;
                margin: 0;
                padding: .3em;
            }
            .date {
                background: #4CAF50;
                color: white;
                padding-right: .5em;
                margin-right: .5em;
                text-transform: capitalize;
            }
        </style></head>""".trimIndent())
    }

    private fun getPriorityTextColor(priority: Int): String{
        return when(priority) {
            Log.VERBOSE -> LOG_VERBOSE_TEXT_COLOR
            Log.DEBUG -> LOG_DEBUG_TEXT_COLOR
            Log.INFO -> LOG_INFO_TEXT_COLOR
            Log.WARN -> LOG_WARN_TEXT_COLOR
            Log.ERROR -> LOG_ERROR_TEXT_COLOR
            Log.ASSERT -> LOG_ASSERT_TEXT_COLOR
            else -> LOG_VERBOSE_TEXT_COLOR
        }
    }
}