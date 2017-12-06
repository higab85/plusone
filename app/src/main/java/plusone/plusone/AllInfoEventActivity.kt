package plusone.plusone

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_all_info_event.*
import org.w3c.dom.Text

class AllInfoEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_info_event)
        val allInfoEventName =this.intent.getStringExtra("allInfoEventName")
        val allInfoEventNameTextView: TextView = findViewById<TextView>(R.id.allInfoEventName)
        allInfoEventNameTextView.text = allInfoEventName

        val event:Event = this.intent.getSerializableExtra("event") as Event

        val allInfoStart = event.start
        val allInfoStartTextView: TextView = findViewById<TextView>(R.id.allInfoStart)
        allInfoStartTextView.text = allInfoStart

        val allInfoEnd = event.end
        val allInfoEndTextView: TextView = findViewById<TextView>(R.id.allInfoEnd)
        allInfoEndTextView.text = allInfoEnd

        val allInfoDescription = event.description
        val allInfoDescriptionTextView: TextView = findViewById<TextView>(R.id.allInfoDescription)
        allInfoDescriptionTextView.text = allInfoDescription

        val allInfoEventType = event.type
        val allInfoEventTypeTextView: TextView = findViewById<TextView>(R.id.allInfoEventType)
        allInfoEventTypeTextView.text = allInfoEventType

        val allInfoPeopleNeeded = event.reqPeople
        val allInfoPeopleNeededTextView: TextView = findViewById<TextView>(R.id.allInfoPeopleNeeded)
        allInfoPeopleNeededTextView.text = allInfoPeopleNeeded.toString()

        //val allInfoLatitude =this.intent.getStringExtra("allInfoLatitude")
        //val allInfoLongitude =this.intent.getStringExtra("allInfoLongitude")

        val allInfoLocation =this.intent.getStringExtra("allInfoLocation")



        Subscribe.setOnClickListener {
            toggleSubscribeEvent(event).execute()
        }

        Unsubscribe.setOnClickListener {
            toggleSubscribeEvent(event).execute()
        }

        SeeMapButton.setOnClickListener{
            ///val intent = Intent(this, MapsActivity::class.java)
            ///intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            ///intent.putExtra("latitude",allInfoLatitude)
            /// intent.putExtra("longitude",allInfoLongitude)

            val gmmIntentUri = Uri.parse("geo:0,0?q="+allInfoLocation+"")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            startActivity(mapIntent)


           /// startActivity(intent)
        }
    }

    inner class toggleSubscribeEvent (private val event:Event): AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            return ServerConnection.toggleSubscriptionToEvent(event)
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }
}
