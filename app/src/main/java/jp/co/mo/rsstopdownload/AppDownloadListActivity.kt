package jp.co.mo.rsstopdownload

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_app_download_list.*
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

class AppDownloadListActivity : AppCompatActivity() {

    private val downloadData by lazy{DownloadData(this, xmlListView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_download_list)
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=25/xml")
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) : AsyncTask<String, Void, String>() {
            private val TAG : String = DownloadData::class.java.simpleName

            var propContext : Context by Delegates.notNull()
            var propListView : ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG, "doInBackground parameter is $params")
                val rssFeed = downloadUrl(params.getOrNull(0))
                if(rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                var parseUtil = ParseFeedData();
                parseUtil.parse(result)

                val feedListAdapter = FeedListAdapter(propContext, parseUtil.feedDatas)
                propListView.adapter = feedListAdapter
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
