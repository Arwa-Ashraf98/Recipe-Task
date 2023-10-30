package com.mad43.recipestask.data.source.network

import com.mad43.recipestask.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(): IRemoteDataSource {
    private lateinit var reader: BufferedReader

    companion object {
        private const val BASE_URL = "https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/recipes.json"
    }

    override suspend fun getAllRecipe(): String {
        var data: String
        withContext(Dispatchers.IO) {
            val url = URL(BASE_URL)
            val httpUrlConnection = setUpHTTPConnection(url = url)
            val responseCode = httpUrlConnection.responseCode
            data = when (responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    // connection ok
                    val inputStream = httpUrlConnection.inputStream
                    val stringBuilderResponse = readResponseData(inputStream)

                    stringBuilderResponse.toString()
                }
                in 400..499 -> {
                    "You Did SomeThing Wrong Check Your Steps"
                }
                else -> {
                    "Server Down Try Later"
                }
            }
        }
        return data
    }

    private fun setUpHTTPConnection(url: URL): HttpURLConnection {
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.requestMethod = AppConstants.GET
        return httpURLConnection
    }

    private fun readResponseData(inputStream: InputStream): StringBuilder? {
        val stringBuilderResponse: StringBuilder?
        try {
            reader = BufferedReader(InputStreamReader(inputStream))
            stringBuilderResponse = StringBuilder()
            var inputLine: String?
            while (reader.readLine().also { inputLine = it } != null) {
                stringBuilderResponse.append(inputLine)
            }
        } finally {
            reader.close()
        }

        return stringBuilderResponse
    }
}




