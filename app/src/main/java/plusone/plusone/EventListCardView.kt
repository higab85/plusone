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
    val searchWord:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_list_events)
        val searchHome = this.intent.getStringExtra("searchHome")

        supportActionBar?.title = "Events"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (searchHome==""){
            refreshEventData().execute()
        }
        else if (searchHome=="Sports Event"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Food"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Party"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Entertainment"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Learning"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Others"){
            refreshEventByType(searchHome).execute()
        }
        else{
            refreshSearchEventData(searchHome).execute()
        }

        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_viewoo) as RecyclerView
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventListCardView)


        val searchButton: ImageButton?  = findViewById<ImageButton>(R.id.imageButtonSearch) as ImageButton


        if (searchButton != null){
            searchButton.setOnClickListener{view->
                val searchBar: EditText = findViewById<EditText>(R.id.editTextSearch2) as EditText
                val searchWord:String = searchBar.text.toString()
                refreshSearchEventData(searchWord).execute()
            }
        }


    }

    // refresh the list: Synchronises local list of events with database on cloud.
    inner class refreshEventData: AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void):Boolean{
            eventsInfoList = DatabaseConnection.getEventsDB()
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventListCardView,eventsInfoList)
            myRecyclerView?.adapter= eventListInfoAdapter
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
            eventListInfoAdapter = EventListInfoAdapter(this@EventListCardView,eventsInfoList)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        override fun onCancelled() {
        }
    }

    // updates the list to only show events which are relevant to search query
    inner class refreshEventByType(private val type:String): AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg params: String):Boolean{
            eventsInfoList = DatabaseConnection.searchEventsByType(type)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventListCardView,eventsInfoList)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        override fun onCancelled() {
        }
    }

}
