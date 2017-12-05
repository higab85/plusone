package plusone.plusone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import android.widget.*
import android.os.AsyncTask
import android.widget.Spinner

class EventCreateActivity : AppCompatActivity() {

    var eventStart:String? = null
//    var eventEnd:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        createEventButton.setOnClickListener{
            val event = Event()
            event.name = eventName.text.toString()
            event.description=description.text.toString()
            // TODO: change to calculatable format
            event.start = eventStart!!
            event.end = end.text.toString()
            event.location = eventAddress.text.toString()
            val spinner = findViewById<Spinner>(R.id.eventType)
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

        val eventStartsAtButton:Button? = findViewById<Button>(R.id.eventStartsAtButton)

        if (eventStartsAtButton != null){
            eventStartsAtButton.setOnClickListener{view->
                funDate()
            }
        }

    }

    fun funTime(date:String){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }

        val dpd = TimePickerDialog(this, android.R.style.Animation_Dialog,
                TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                    eventStart = date + "T${singleDigitParser(hour)}:${singleDigitParser(minute)}"
                },hour, minute, true)

        // show timepicker
        dpd.show()
    }


    fun funDate(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        var returnDate:String? = null

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }

        val dpd = DatePickerDialog(this, android.R.style.Animation_Dialog,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    funTime("$year-${singleDigitParser(monthOfYear+1)}-${singleDigitParser(dayOfMonth)}")

                }, year, month, day)

        // show datepicker
        dpd.show()

    }

    inner class CreateEvent: AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            return ServerConnection.createEvent(params[0])
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }



}


