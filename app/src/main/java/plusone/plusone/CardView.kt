package plusone.plusone

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.util.Log


class CardView : AppCompatActivity() {
     private var eventsInfoList:List<Event>? = null
     var myRecyclerView: RecyclerView? = null
     var eventInfoAdapter: EventInfoAdapter? = null
    val searchWord:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        supportActionBar?.title = "Events"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        refreshEventData().execute()
        myRecyclerView = findViewById(R.id.event_recycler_view) as RecyclerView
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@CardView)


        val searchButton: ImageButton?  = findViewById(R.id.imageButtonSearch) as ImageButton


        if (searchButton != null){
            searchButton.setOnClickListener{view->
                val searchBar: EditText = findViewById(R.id.editTextSearch2) as EditText
                val searchWord:String = searchBar.text.toString()
                refreshSearchEventData(searchWord).execute()
            }
        }

    }


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
