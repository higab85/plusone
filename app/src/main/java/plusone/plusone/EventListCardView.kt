package plusone.plusone

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageButton


// This activity will show an undetailed list of all the events (filtered or not depending on
// whether a filter has been applied or not).
class EventListCardView : AppCompatActivity() {
     private var eventsInfoList:List<Event>? = null
     var myRecyclerView: RecyclerView? = null
     var eventListInfoAdapter: EventListInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_list_events)
        val searchHome:String? = this.intent.getStringExtra("searchHome")
        val filterType = this.intent.getStringExtra("filter type")

        supportActionBar?.title = "Events"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var queryMap = mutableMapOf<String,String>()
        queryMap["type"] = filterType
        queryMap["name"] = searchHome!!

        refreshEventData().execute(queryMap)


        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_viewoo)
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventListCardView)


        val searchButton: ImageButton? = findViewById<ImageButton>(R.id.imageButtonSearch)


        searchButton?.setOnClickListener{view->
            val searchBar: EditText = findViewById<EditText>(R.id.editTextSearch2)
            queryMap["name"] =  searchBar.text.toString()
            refreshEventData().execute(queryMap)
        }
    }

    // refresh the list: Synchronises local list of events with database on cloud.
    inner class refreshEventData: AsyncTask<Map<String,String>, Void, Boolean>() {

        override fun doInBackground(vararg params: Map<String,String>):Boolean{
            eventsInfoList = ServerConnection.getEvents(params[0])
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            myRecyclerView?.adapter= eventListInfoAdapter
        }
    }

}
