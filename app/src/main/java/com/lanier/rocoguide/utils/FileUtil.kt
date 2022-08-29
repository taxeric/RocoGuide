package com.lanier.rocoguide.utils

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/29 14:39
 * Desc  :
 */
var defaultLocalJsonDataPath = ""

fun InputStream.copyStreamToFile(outputFile: File) {
    this.use { input ->
        val outputStream = FileOutputStream(outputFile)
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024) // buffer size
            while (true) {
                val byteCount = input.read(buffer)
                if (byteCount < 0) break
                output.write(buffer, 0, byteCount)
            }
            output.flush()
        }
    }
}
