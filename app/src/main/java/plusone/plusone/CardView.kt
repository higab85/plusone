package plusone.plusone

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import java.util.*
import kotlin.collections.ArrayList


// This activity will show an undetailed list of all the events (filtered or not depending on
// whether a filter has been applied or not).
class CardView : AppCompatActivity() {
     private var eventsInfoList:List<Event>? = null
     var myRecyclerView: RecyclerView? = null
     var eventInfoAdapter: EventInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        supportActionBar?.title = "Events"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        refreshEventData().execute()
        myRecyclerView = findViewById(R.id.event_recycler_view) as RecyclerView
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@CardView)
    }

    // refresh the list: Synchronises local list of events with database on cloud.
    inner class refreshEventData: AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void):Boolean{
            eventsInfoList = DatabaseConnection.getEventsDB()
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventInfoAdapter=EventInfoAdapter(this@CardView,eventsInfoList)
            myRecyclerView?.adapter=eventInfoAdapter
        }

        override fun onCancelled() {
        }
    }

    // updates the list to only show events which are relevant to search query
    inner class refreshSearchEventData(private val searchObject:String): AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg params: String):Boolean{
            eventsInfoList = DatabaseConnection.searchEventsDB(searchObject)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventInfoAdapter=EventInfoAdapter(this@CardView,eventsInfoList)
            myRecyclerView?.adapter=eventInfoAdapter
        }

        override fun onCancelled() {
        }
    }

}
