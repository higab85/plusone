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
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_all_info_event.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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

    fun funTime(view: View, date:String){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val filterButton:Button? = findViewById(R.id.filterButton) as Button

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


    fun funDate(view: View){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val filterButton:Button? = findViewById(R.id.filterButton) as Button
        var returnDate:String? = null

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }

        val dpd = DatePickerDialog(this, android.R.style.Animation_Dialog,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    funTime(view, "$year-${singleDigitParser(monthOfYear+1)}-${singleDigitParser(dayOfMonth)}")

                }, year, month, day)

        // show datepicker
        dpd.show()

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


