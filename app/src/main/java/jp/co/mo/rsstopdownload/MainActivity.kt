package jp.co.mo.rsstopdownload

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml")
    }

    companion object {
        private class DownloadData : AsyncTask<String, Void, String>() {
            private val TAG : String = DownloadData::class.java.simpleName

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute parameter is ${result.toString()}")
            }

            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG, "doInBackground parameter is $params")
                for (p in params) {
                    print(p)
                }
                val rssFeed = downloadUrl(params.getOrNull(0))
                if(rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadUrl(url : String?): String {
                Log.d(TAG, "downloadUrl parameter is $url")
                try {
                    return URL(url).readText()
                } catch (e: Exception) {
                    val message = when(e) {
                        is MalformedURLException -> "MalformedURLException: ${e.message}"
                        is IOException -> "IOException: ${e.message}"
                        is SecurityException -> "SecurityException: ${e.message}"
                        else -> "Unknow error: ${e.message}"
                    }
                    Log.e(TAG, message)
                }
                return ""
            }


        }
    }
}
