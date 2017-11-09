package plusone.plusone

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import android.widget.*
import android.os.AsyncTask
import android.view.View
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_all_info_event.*


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
            event.location = eventAddress.text.toString()
            val spinner = findViewById(R.id.eventType) as Spinner
            event.type = spinner.getSelectedItem().toString();
            event.reqPeople = peopleNeeded.text.toString().toInt()
            CreateEvent().execute(event)

        }
        addAddressButon.setOnClickListener{
            val gmmIntentUri = Uri.parse("geo:0,0?q="+eventAddress.text.toString()+"")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            startActivity(mapIntent)

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


