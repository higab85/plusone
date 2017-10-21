package plusone.plusone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import android.widget.*
import android.os.AsyncTask
import android.view.View


class EventCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        createEventButton.setOnClickListener{
            val event = Event()
            event.name = eventName.text.toString()
            event.description=description.text.toString()
            // TODO: change to calculatable format
            event.start = start.text.toString()
            event.end = end.text.toString()
            event.location = location.text.toString()
            //event.type = EventType.PARTY
            event.reqPeople = peopleNeeded.text.toString().toInt()
            CreateEvent().execute(event)
        }

    }
    inner class CreateEvent: AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            return DatabaseConnection.createEventDB(params[0])
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }
}


