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
    var eventEnd:String? = null
//    var eventEnd:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val nameFrom =this.intent.getStringExtra("name")
        eventName.setText(nameFrom)
        val descriptionFrom =this.intent.getStringExtra("description")
        description.setText(descriptionFrom)
        val startFrom =this.intent.getStringExtra("start")
        start.setText(startFrom)
        val endFrom =this.intent.getStringExtra("end")
        end.setText(endFrom)
        val addressFrom = this.intent.getStringExtra("address")
        eventAddress.setText(addressFrom)

        val latitudeFrom = this.intent.getDoubleExtra("latitude",0.0)
        val longitudeFrom = this.intent.getDoubleExtra("longitude",0.0)


        createEventButton.setOnClickListener{
            val event = Event()
            event.name = eventName.text.toString()
            event.description=description.text.toString()
            // TODO: change to calculatable format
            System.out.println(eventStart)
            System.out.println(eventEnd)
            event.start = eventStart!!
            event.end = eventEnd!!
            event.location = eventAddress.text.toString()
            val spinner = findViewById<Spinner>(R.id.eventType)
            event.type = spinner.getSelectedItem().toString();
            event.reqPeople = peopleNeeded.text.toString().toInt()
            event.latitude = latitudeFrom
            event.longitude = longitudeFrom

            CreateEvent().execute(event)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        addAddressButon.setOnClickListener{View->
            /// val gmmIntentUri = Uri.parse("geo:0,0?q="+eventAddress.text.toString()+"")
            ///val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            ///mapIntent.`package` = "com.google.android.apps.maps"
            ///startActivity(mapIntent)
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("name",eventName.text.toString() )
            intent.putExtra("description",description.text.toString() )
            intent.putExtra("start",start.text.toString() )
            intent.putExtra("end",end.text.toString() )
            intent.putExtra("address",eventAddress.text.toString() )
            //intent.putExtra("reqPeople",peopleNeeded.text.toString().toInt() )
            startActivity(intent)
        }

        val eventStartsAtButton:Button? = findViewById<Button>(R.id.eventStartsAtButton)

        if (eventStartsAtButton != null){
            eventStartsAtButton.setOnClickListener{view->
                 funDate(true)
            }
        }
        val eventEndsAtButton:Button? = findViewById<Button>(R.id.eventEndsAtButton)

        if (eventEndsAtButton != null){
            eventEndsAtButton.setOnClickListener{view->
                funDate(false)
            }
        }

    }


    var time:String = ""
    fun funTime(date:String?, start:Boolean){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }

        var dpd:TimePickerDialog?
        if(start)
             dpd = TimePickerDialog(this, android.R.style.Animation_Dialog,
                    TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                        eventStart = date + "T${singleDigitParser(hour)}:${singleDigitParser(minute)}"
                    },hour, minute, true)
        else
            dpd = TimePickerDialog(this, android.R.style.Animation_Dialog,
                    TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                        eventEnd = date + "T${singleDigitParser(hour)}:${singleDigitParser(minute)}"
                    },hour, minute, true)

        // show timepicker
        dpd.show()
    }


    fun funDate(start:Boolean){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }

        val dpd = DatePickerDialog(this, android.R.style.Animation_Dialog,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    funTime("$year-${singleDigitParser(monthOfYear+1)}-${singleDigitParser(dayOfMonth)}", start)
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


