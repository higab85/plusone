package plusone.plusone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import android.widget.*
import android.os.AsyncTask
import android.view.View


class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)


//        val spinner = findViewById(R.id.eventType) as Spinner
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        val adapter = ArrayAdapter.createFromResource(this,
//                R.array.arrayEventTypes, android.R.layout.simple_spinner_item)
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        // Apply the adapter to the spinner
//        spinner.adapter = adapter
//
//        var eventTypeResult:String = "Type not selected"

//        var nameButton:EditText = findViewById(R.id.eventName) as EditText
//        var dateButton:EditText = findViewById(R.id.date) as EditText
//        var startButton:EditText = findViewById(R.id.start) as EditText
//        var endButton:EditText = findViewById(R.id.end) as EditText
//        var locationButton:EditText = findViewById(R.id.location) as EditText

//        var reqPeopleButton:EditText = findViewById(R.id.peopleNeeded) as EditText

        createEventButton.setOnClickListener{
            val event = Event()
            event.name = eventName.text.toString()
            event.description = description.text.toString()
            // TODO: change to calculatable format
            event.start = start.text.toString()
            event.end = end.text.toString()
            event.location = location.text.toString()
            event.type = EventType.OTHER
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

//    inner class SpinnerActivity : AppCompatActivity : OnItemSelectedListener {
//
//        public void onItemSelected(AdapterView<?> parent, View view,
//                int pos, long id) {
//            // An item was selected. You can retrieve the selected item using
//            // parent.getItemAtPosition(pos)
//        }
//
//        public void onNothingSelected(AdapterView<?> parent) {
//            // Another interface callback
//        }
    //}

}


