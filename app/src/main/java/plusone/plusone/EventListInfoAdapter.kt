package plusone.plusone

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
//import com.example.kotlin.myapplication.R
import android.content.Context
import android.content.Intent
import java.io.Serializable

/**
 * Created by Javicraft on 19/10/2017.
 */
class EventListInfoAdapter(private val myContext: Context, private val eventsInfoList:List<Event>?): RecyclerView.Adapter<EventListInfoAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var id: String = "100"
        var nameEvent: TextView
        var locationEvent: TextView
        var startEvent: TextView
        var endEvent: TextView
        var descriptionEvent:TextView
        var typeEvent:TextView
        var reqPeopleEvent:TextView
        var latitude:String = ""
        var longitude:String=""


        init {

            nameEvent=itemview.findViewById<TextView>(R.id.event_name)
            locationEvent = itemview.findViewById<TextView>(R.id.event_location)
            startEvent = itemview.findViewById<TextView>(R.id.event_start)
            endEvent = itemview.findViewById<TextView>(R.id.event_end)
            descriptionEvent=itemview.findViewById<TextView>(R.id.event_type)
            typeEvent=itemview.findViewById<TextView>(R.id.event_description)
            reqPeopleEvent=itemview.findViewById<TextView>(R.id.event_reqPeople)


        }
        fun bind(event: Event){
            itemView.setOnClickListener{View->
                //Toast.makeText(itemView.context,"Boton pulsado:" + eventsInfoList.name,Toast.LENGTH_LONG).show()
                val intent = Intent(myContext, AllInfoEventActivity::class.java)
                intent.putExtra("event", event as Serializable)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_card_view_list_events, parent, false)
        val viewHolder = MyViewHolder(itemLayoutView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eventInfo = eventsInfoList!![position]
        holder.id = eventInfo.id
        holder.nameEvent.text = eventInfo.name
        holder.locationEvent.text = eventInfo.location
        holder.startEvent.text = eventInfo.start
        holder.endEvent.text = eventInfo.end

        holder.descriptionEvent.text=eventInfo.description
        holder.typeEvent.text=eventInfo.type
        holder.reqPeopleEvent.text=eventInfo.reqPeople.toString()
        holder.bind(eventsInfoList[position])

    }

    override fun getItemCount(): Int {
        if(eventsInfoList==null)
            return 0
        else
            return eventsInfoList.size
    }
}
