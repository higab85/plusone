package plusone.plusone

import android.app.DatePickerDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.app.SearchManager
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.view.View
import android.widget.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// This activity will show an undetailed list of all the events (filtered or not depending on
// whether a filter has been applied or not).
class EventList : AppCompatActivity() {
    private var eventsInfoList:List<Event>? = null
    private var eventsInfoListCurated:List<Event>? = null

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
        else if (searchHome=="SPORTS EVENT"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="FOOD"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="PARTY"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="ENTERTAINMENT"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="LEARNING"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="OTHER"){
            refreshEventByType(searchHome).execute()
        }
        else{
            refreshSearchEventData(searchHome).execute()
        }

        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_view)
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventList)


        val searchButton: Button?  = findViewById<Button>(R.id.imageButtonSearch)
        val timeSortButton: Button?  = findViewById<Button>(R.id.sortTimeButton)
        val distanceSortButton: Button?  = findViewById<Button>(R.id.sortDistanceButton)
        val filterButton:Button? = findViewById<Button>(R.id.filterButton)

        if (searchButton != null){
            searchButton.setOnClickListener{view->
                val searchBar: EditText = findViewById<EditText>(R.id.editTextSearch2)
                val searchWord:String = searchBar.text.toString()
                eventsInfoListCurated = LocalEventFilter.searchEventByName(eventsInfoList, searchWord)
                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoListCurated)
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
    }

    fun funTime(view: View, date:String){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_view)
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventList)

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }
        val dpd = TimePickerDialog(this, android.R.style.Animation_Dialog,
                TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->

                    val time = LocalDateTime.parse(date + "T${singleDigitParser(hour)}:${singleDigitParser(minute)}", DateTimeFormatter.ISO_DATE_TIME)

                    eventsInfoListCurated = LocalEventFilter.filterByStartTimeAfter(eventsInfoList!!, time)
                    eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoListCurated)
                    myRecyclerView?.adapter= eventListInfoAdapter
                },hour, minute, true)

        // show timepicker
        dpd.show()
    }


    fun funDate(view: View){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val filterButton:Button? = findViewById<Button>(R.id.filterButton)
        var returnDate:String? = null

        fun singleDigitParser(value:Int):String{
            if(value < 10)
                return "0$value"
            return value.toString()
        }

        val dpd = DatePickerDialog(this, android.R.style.Animation_Dialog,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    // MonthOfYear + 1 because january is month 0
                        funTime(view, "$year-${singleDigitParser(monthOfYear + 1)}-${singleDigitParser(dayOfMonth)}")

                }, year, month, day)

        // show datepicker
        dpd.show()

    }


    override fun onNewIntent(intent:Intent) {
        handleIntent(intent)
    }


     private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
        }
    }


    // refresh the list: Synchronises local list of events with database on cloud.
    inner class refreshEventData: AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void):Boolean{
            eventsInfoList = ServerConnection.getEvents()
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
            val query = mapOf("name" to searchObject)
            eventsInfoList = ServerConnection.getEvents(query)
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
            val query = mapOf("type" to type)
            eventsInfoList = ServerConnection.getEvents(query)
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