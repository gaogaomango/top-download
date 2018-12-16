package jp.co.mo.rsstopdownload

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class FeedListAdapter(context: Context, private val feedDatas: List<FeedEntry>): BaseAdapter() {

    private val TAG = FeedListAdapter::class.simpleName
    private val inflator = LayoutInflater.from(context)


    override fun getItem(position: Int): Any {
        return feedDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return feedDatas.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflator.inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentFeedEntry = feedDatas[position]

        viewHolder.tvName.text = currentFeedEntry.name
        viewHolder.tvArtist.text = currentFeedEntry.artist
        viewHolder.tvSummary.text = currentFeedEntry.summary

        return view
    }

}

class ViewHolder(v: View) {
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
}
