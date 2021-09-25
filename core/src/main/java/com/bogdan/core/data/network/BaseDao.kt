package com.bogdan.core.data.network

import android.content.Context
import com.bogdan.core.R
import java.nio.charset.Charset

open class BaseDao {

    fun getFileData(
        context: Context,
        fileToOpen: Int
    ): String {
        val file = context.resources.openRawResource(fileToOpen)
        val size = file.available()
        val buffer = ByteArray(size)
        file.read(buffer)
        file.close()
        return String(buffer, Charset.defaultCharset())
    }
}