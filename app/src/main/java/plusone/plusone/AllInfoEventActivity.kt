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
        val id = this.intent.getStringExtra("id")
        val id2 = intent.getIntExtra("id", 0)


        val allInfoStart =this.intent.getStringExtra("allInfoStart")
        val allInfoStartTextView: TextView = findViewById<TextView>(R.id.allInfoStart)
        allInfoStartTextView.text = allInfoStart

        val allInfoEnd =this.intent.getStringExtra("allInfoEnd")
        val allInfoEndTextView: TextView = findViewById<TextView>(R.id.allInfoEnd)
        allInfoEndTextView.text = allInfoEnd

        val allInfoDescription =this.intent.getStringExtra("allInfoDescription")
        val allInfoDescriptionTextView: TextView = findViewById<TextView>(R.id.allInfoDescription)
        allInfoDescriptionTextView.text = allInfoDescription

        val allInfoEventType =this.intent.getStringExtra("allInfoEventType")
        val allInfoEventTypeTextView: TextView = findViewById<TextView>(R.id.allInfoEventType)
        allInfoEventTypeTextView.text = allInfoEventType

        val allInfoPeopleNeeded =this.intent.getStringExtra("allInfoPeopleNeeded")
        val allInfoPeopleNeededTextView: TextView = findViewById<TextView>(R.id.allInfoPeopleNeeded)
        allInfoPeopleNeededTextView.text = allInfoPeopleNeeded

        //val allInfoLatitude =this.intent.getStringExtra("allInfoLatitude")
        //val allInfoLongitude =this.intent.getStringExtra("allInfoLongitude")

        val allInfoLocation =this.intent.getStringExtra("allInfoLocation")



        Subscribe.setOnClickListener {
            val subscribeButton = subscribeEvent(0, id2).execute()
        }

        Unsubscribe.setOnClickListener {
                val Unsubscribe = unsubscribeEvent(0,id2).execute()
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
    // TODO cambiar a serverconnection
    inner class subscribeEvent (private val idevent: Int, private val iduser: Int): AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            return DatabaseConnection.subscribeEventDB(idevent, iduser)
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }
    inner class unsubscribeEvent (private val idevent: Int, private val iduser: Int): AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            return DatabaseConnection.dessubscribeEventDB(idevent, iduser)
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }
}
