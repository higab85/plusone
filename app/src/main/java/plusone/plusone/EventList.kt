package plusone.plusone

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.app.SearchManager
import android.content.Intent
import android.widget.*


// This activity will show an undetailed list of all the events (filtered or not depending on
// whether a filter has been applied or not).
class EventList : AppCompatActivity() {
    private var eventsInfoList:List<Event>? = null
    var myRecyclerView: RecyclerView? = null
    var eventListInfoAdapter: EventListInfoAdapter? = null
    val searchWord:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        val searchHome = this.intent.getStringExtra("searchHome")


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

        myRecyclerView = findViewById(R.id.event_list_recycler_view) as RecyclerView
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventList)

        val searchButton: Button?  = findViewById(R.id.imageButtonSearch) as Button
        val timeSortButton: Button?  = findViewById(R.id.sortTimeButton) as Button
        val distanceSortButton: Button?  = findViewById(R.id.sortDistanceButton) as Button


        if (searchButton != null){
            searchButton.setOnClickListener{view->
                val searchBar: EditText = findViewById(R.id.editTextSearch2) as EditText
                val searchWord:String = searchBar.text.toString()
                eventsInfoList = LocalEventFilter.searchEventByName(eventsInfoList, searchWord)
                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList)
                myRecyclerView?.adapter= eventListInfoAdapter
            }
        }

        if (timeSortButton != null){
            timeSortButton.setOnClickListener{view->
                eventsInfoList = LocalEventFilter.orderTimeFirstToLast(eventsInfoList!!)
                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList)
                myRecyclerView?.adapter= eventListInfoAdapter
            }
        }

//        if (distanceSortButton != null){
//            distanceSortButton.setOnClickListener{view->
//                eventsInfoList = LocalEventFilter.orderDistanceClosestFirst(eventsInfoList)
//                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList)
//                myRecyclerView?.adapter= eventListInfoAdapter
//            }
//        }

    }


    override fun onNewIntent(intent:Intent) {
        handleIntent(intent)
    }


     private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            Toast.makeText(this,"Testing: " + query,Toast.LENGTH_SHORT).show()
        }
    }


    // refresh the list: Synchronises local list of events with database on cloud.
    inner class refreshEventData: AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void):Boolean{
            eventsInfoList = DatabaseConnection.getEventsDB()
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            myRecyclerView?.adapter = EventListInfoAdapter(this@EventList,eventsInfoList)
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
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList)
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
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        override fun onCancelled() {
        }
    }

}