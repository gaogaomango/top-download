package jp.co.mo.rsstopdownload

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showDownloadContent(v: View) {
        val intent = Intent(this, AppDownloadListActivity::class.java)
        // TODO: putExtra about page info to transition
        startActivity(intent)
    }
}
