package plusone.plusone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.ArrayAdapter
import android.Manifest.permission.INTERNET




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

        createEventButton.setOnClickListener{
            var event = Event()
            event.name = eventName.text.toString()
            // TODO: change to calculatable format
//            event.start = // date.text.toString() + " at " + start.text.toString()
//            event.end = // date.text.toString() + " at " + end.text.toString()
//            event.location = // location.text.toString()
//            event.type = eventType.toString()
//            event.reqPeople = 1 // peopleNeeded.text.toString().toInt()
            event.name = "test-titleee"
            event.start = "02/04/2017 at 02:00"
            event.end = "02/04/2017 at 02:30"
            event.location = "club"
            event.type = EventType.CONCERT
            event.reqPeople = 1
            DatabaseConnection.createEventDB(event)

            finish()
        }

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
//
//            }
//
//        }

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
