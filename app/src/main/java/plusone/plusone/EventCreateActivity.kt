package plusone.plusone

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import android.widget.*
import android.os.AsyncTask
import android.widget.Spinner
import android.provider.ContactsContract.CommonDataKinds.Phone



class EventCreateActivity : AppCompatActivity() {

    var eventStart:String? = null
    var eventEnd:String? = null
//    var eventEnd:String? = null
    private val ID = 1

    var latitudeFrom = 0.0
    var longitudeFrom = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        var isEditTrue:String = ""
        isEditTrue = this.intent.getStringExtra("activateEdit")
        if(isEditTrue!="" && isEditTrue=="isTrue"){
            val eventToModify:Event = this.intent.getSerializableExtra("event") as Event
            eventName.setText(eventToModify.name)
            description.setText(eventToModify.description)
            createEventButton.setText("EDIT")
        }


        val nameFrom =this.intent.getStringExtra("name")
        eventName.setText(nameFrom)
        val descriptionFrom =this.intent.getStringExtra("description")
        description.setText(descriptionFrom)
        val startFrom =this.intent.getStringExtra("start")
        //textViewTimeStart.setText(startFrom)
        val endFrom =this.intent.getStringExtra("end")
        //end.setText(endFrom)
        val addressFrom = this.intent.getStringExtra("address")
        eventAddress.setText(addressFrom)


        createEventButton.setOnClickListener{
            var event = Event()

            if(isEditTrue!=""){
                val eventToModify:Event = this.intent.getSerializableExtra("event") as Event
                if(eventToModify!=null){
                    event = eventToModify
                }
            }

            if(eventName.text.toString()!=null){event.name = eventName.text.toString()}
            if(description.text.toString()!=null){event.description=description.text.toString()}
            // TODO: change to calculatable format
            System.out.println(eventStart)
            System.out.println(eventEnd)
            if(eventStart!!!=null){event.start = eventStart!!}
            if(eventEnd!!!=null){event.end = eventEnd!!}
            if(eventAddress.text.toString()!=null){event.location = eventAddress.text.toString()}
            val spinner = findViewById<Spinner>(R.id.eventType)
            if(spinner.getSelectedItem().toString()!=null){event.type = spinner.getSelectedItem().toString()}
            if(peopleNeeded.text.toString().toInt()!=null){event.reqPeople = peopleNeeded.text.toString().toInt()}
            if(latitudeFrom.toString()!=null){event.latitude = latitudeFrom.toString()}
            if(longitudeFrom.toString()!=null){event.longitude = longitudeFrom.toString()}


            if(isEditTrue!=null){
                EditEvent().execute(event)
            }
            else{
                CreateEvent().execute(event)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        addAddressButon.setOnClickListener{View->
            /// val gmmIntentUri = Uri.parse("geo:0,0?q="+eventAddress.text.toString()+"")
            ///val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            ///mapIntent.`package` = "com.google.android.apps.maps"
            ///startActivity(mapIntent)
            val intent = Intent(this, MapsActivity::class.java)
            //intent.putExtra("name",eventName.text.toString() )
            //intent.putExtra("description",description.text.toString() )
            //intent.putExtra("start",start.text.toString() )
           // intent.putExtra("end",end.text.toString() )
            intent.putExtra("address",eventAddress.text.toString() )
            //intent.putExtra("reqPeople",peopleNeeded.text.toString().toInt() )
            startActivityForResult(intent,ID)
        }

        val eventStartsAtButton:Button? = findViewById<Button>(R.id.textViewTimeStart)

        if (eventStartsAtButton != null){
            eventStartsAtButton.setOnClickListener{view->
                 funDate(true)
            }
        }
        if(eventStartsAtButton==null){
            time=""
        }
        if(eventEndsAtButton==null){
            time=""
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

    inner class EditEvent: AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            return ServerConnection.modifyEvent(params[0])
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }

    /**override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // Check which request we're responding to
        if (requestCode == ID) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {

                latitudeFrom = data.getDoubleExtra("latitude",0.0)
                longitudeFrom = data.getDoubleExtra("longitude",0.0)

            }
        }
    }**/


}


