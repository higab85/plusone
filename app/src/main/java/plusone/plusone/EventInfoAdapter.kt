package plusone.plusone

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.w3c.dom.Text
//import com.example.kotlin.myapplication.R
import android.content.Context
import java.util.ArrayList


/**
 * Created by Javicraft on 19/10/2017.
 */
class EventInfoAdapter(private val myContext: Context, private val eventsInfoList:ArrayList<Event>): RecyclerView.Adapter<EventInfoAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var nameEvent: TextView
        var locationEvent: TextView
        var startEvent: TextView
        var endEvent: TextView

        init {
            nameEvent=itemview.findViewById<TextView>(R.id.event_name)
            locationEvent = itemview.findViewById<TextView>(R.id.event_location)
            startEvent = itemview.findViewById<TextView>(R.id.event_start)
            endEvent = itemview.findViewById<TextView>(R.id.event_end)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_card_view, parent, false)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eventInfo = eventsInfoList[position]
        holder.nameEvent.text = eventInfo.name
        holder.locationEvent.text = eventInfo.location
        holder.startEvent.text = eventInfo.start
        holder.endEvent.text = eventInfo.end


    }

    override fun getItemCount(): Int {
        return eventsInfoList.size
    }
}
