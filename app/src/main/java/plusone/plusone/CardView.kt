package plusone.plusone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import java.util.*
import kotlin.collections.ArrayList

class CardView : AppCompatActivity() {
     val eventsInfoList:ArrayList<Event> = ArrayList<Event>()
     var myRecyclerView: RecyclerView? = null
     var eventInfoAdapter: EventInfoAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        supportActionBar?.title = "Events"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        EventData()
        myRecyclerView = findViewById(R.id.event_recycler_view) as RecyclerView
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@CardView)
        eventInfoAdapter=EventInfoAdapter(this@CardView,eventsInfoList)
        myRecyclerView?.adapter=eventInfoAdapter

        /*
          eventInfoAdapter = EventInfoAdapter(eventsInfoList)
          val mLayoutManager = LinearLayoutManager(applicationContext)
          recyclerView!!.layoutManager = mLayoutManager
          recyclerView!!.itemAnimator = DefaultItemAnimator()
          recyclerView!!.adapter = eventInfoAdapter

          EventData()
      }
      */
    }
      private fun EventData() {
          var event = Event("Concierto Mozart", "Teatro Real", "19:30", "22:30")
          eventsInfoList.add(event)

          event = Event("Star Wars VIII", "Kinepolis", "20:30", "22:15")
          eventsInfoList.add(event)

          event = Event("Torneo LOL", "En cada casa", "12:30", "16:30")
          eventsInfoList.add(event)

          //eventInfoAdapter!!.notifyDataSetChanged()
      }

}
