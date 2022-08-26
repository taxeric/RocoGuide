package com.lanier.rocoguide.ui.glance

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import com.lanier.rocoguide.entity.NewsData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2022/8/26 17:14
 * Desc  :
 */
object NewsGlanceDefinition: GlanceStateDefinition<NewsData> {

    private val DATA_STORE_FILENAME = "newsData"
    private val Context.datastore by dataStore(DATA_STORE_FILENAME, NewsSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<NewsData> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    object NewsSerializer: Serializer<NewsData>{
        override val defaultValue: NewsData
            get() = NewsData(title = "loading...")

        override suspend fun readFrom(input: InputStream): NewsData = try {
            Json.decodeFromString(
                NewsData.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Could not read weather data: ${exception.message}")
        }

        override suspend fun writeTo(t: NewsData, output: OutputStream) {
            output.use {
                it.write(
                    Json.encodeToString(NewsData.serializer(), t).encodeToByteArray()
                )
            }
        }
    }
}