package jp.co.mo.rsstopdownload

import android.os.Build
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseUtil {
    private val TAG = ParseUtil::class.simpleName

    val feedDatas = ArrayList<FeedEntry>();


    fun parse(xmlData: String): Boolean {
        Log.d(TAG, "parse called with $xmlData")
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                var tagName = xpp.name?.toLowerCase()
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "parse: Starting tag for " + tagName)
                        if ("entry".equals(tagName)) {
                            inEntry = true
                        }
                    }

                    XmlPullParser.TEXT -> {
                        Log.d(TAG, "parse: text for " + xpp.text)
                        textValue = xpp.text
                    }
                    XmlPullParser.END_TAG -> {
Log.d(TAG, "parse: Ending tag for " + tagName)
                        if(inEntry) {
                            when(tagName) {
                                "entry" -> {
                                    feedDatas.add(currentRecord)
                                    inEntry = false
                                    currentRecord = FeedEntry()
                                }
                                "name" -> currentRecord.name = textValue
                                "artist" -> currentRecord.artist = textValue
                                "releasedate" -> currentRecord.releaseDate = textValue
                                "summary" -> currentRecord.summary = textValue
                                "image" -> currentRecord.imageURL = textValue
                            }
                        }
                    }
                }

                eventType = xpp.next()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                feedDatas.stream().forEach { i -> print(i.toString()) }
            } else {
                for (feedData in feedDatas) {
                    print(feedData.toString())
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}