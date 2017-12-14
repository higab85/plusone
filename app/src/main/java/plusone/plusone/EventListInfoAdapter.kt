package plusone.plusone

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
//import com.example.kotlin.myapplication.R
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import java.io.Serializable

/**
 * Created by Javicraft on 19/10/2017.
 */
class EventListInfoAdapter(private val myContext: Context, private val eventsInfoList:List<Event>?, private val deviceLatitude:Double, private val deviceLongitude:Double): RecyclerView.Adapter<EventListInfoAdapter.MyViewHolder>(){

    var PI_RAD = Math.PI / 180.0

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var id: String = "100"
        var nameEvent: TextView
        var locationEvent: TextView
        var startEvent: TextView
        var endEvent: TextView
        var descriptionEvent:TextView
        var typeEvent:TextView
        var reqPeopleEvent:TextView
        var distance:TextView
        var latitude:Double = 0.0
        var longitude:Double= 0.0


        init {

            nameEvent=itemview.findViewById<TextView>(R.id.event_name)
            locationEvent = itemview.findViewById<TextView>(R.id.event_location)
            startEvent = itemview.findViewById<TextView>(R.id.event_start)
            endEvent = itemview.findViewById<TextView>(R.id.event_end)
            descriptionEvent=itemview.findViewById<TextView>(R.id.event_type)
            typeEvent=itemview.findViewById<TextView>(R.id.event_description)
            reqPeopleEvent=itemview.findViewById<TextView>(R.id.event_reqPeople)
            distance=itemview.findViewById<TextView>(R.id.event_distance)


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
        holder.startEvent.text = "Start: "+eventInfo.start.subSequence(0,10)+" At: "+eventInfo.start.subSequence(11,16)
        holder.endEvent.text = "  End: "+eventInfo.end.subSequence(0,10)+" At: "+eventInfo.end.subSequence(11,16)

        holder.descriptionEvent.text=eventInfo.description
        holder.typeEvent.text=eventInfo.type
        holder.reqPeopleEvent.text=eventInfo.reqPeople.toString()
        if((eventInfo.latitude=="0.0" && eventInfo.longitude=="0.0") || (eventInfo.latitude=="" && eventInfo.longitude=="" || eventInfo.latitude=="0.0" || eventInfo.latitude=="" || (eventInfo.latitude > "90" && eventInfo.latitude < "0")) && (eventInfo.longitude=="0.0" || eventInfo.longitude=="" || (eventInfo.longitude > "180" && eventInfo.longitude < "-180")) && (deviceLatitude==null || deviceLatitude==0.0) && (deviceLongitude== null || deviceLongitude== 0.0)){
            holder.distance.text = "No localisation for this event"
        }else{
            var distanceResults = FloatArray(1)
            var temp = Location.distanceBetween(eventInfo.latitude.toDouble(),eventInfo.longitude.toDouble(), deviceLatitude,deviceLongitude,distanceResults)
            holder.distance.text = "Distance: "+(distanceResults[0]/1000).toString()+"Km"
        }
        holder.bind(eventsInfoList[position])

    }

    override fun getItemCount(): Int {
        if(eventsInfoList==null)
            return 0
        else
            return eventsInfoList.size
    }

    fun distance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
        val phi1 = lat1 * PI_RAD
        val phi2 = lat2 * PI_RAD
        val lam1 = long1 * PI_RAD
        val lam2 = long2 * PI_RAD

        return 6371.01 * Math.acos(Math.sin(phi1) * Math.sin(phi2) + Math.cos(phi1) * Math.cos(phi2) * Math.cos(lam2 - lam1))
    }
}
