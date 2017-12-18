package plusone.plusone

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mysql.fabric.Server
import kotlinx.android.synthetic.main.activity_all_info_event.*
import org.w3c.dom.Text
import java.io.Serializable

class AllInfoEventActivity : AppCompatActivity() {

    var peopleSubscribed:Int = 0
    var subscribedOrNot:Boolean = false
    var event:Event?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        event = this.intent.getSerializableExtra("event") as Event
        getData(event!!).execute()

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

    inner class getData (private val event:Event): AsyncTask<Event, Void, Boolean>() {

        override fun doInBackground(vararg params: Event): Boolean {
            peopleSubscribed = ServerConnection.getUsersSubscribedTo(event)!!.count()
            subscribedOrNot = ServerConnection.isAttending(event)!!
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            loadActivity()
        }

        override fun onCancelled() {
        }
    }

    fun loadActivity(){
        System.out.println("loadActivity")
        setContentView(R.layout.activity_all_info_event)

        val allInfoEventNameTextView: TextView = findViewById<TextView>(R.id.allInfoEventName)
        allInfoEventNameTextView.text = event!!.name

        val allInfoStartTextView: TextView = findViewById<TextView>(R.id.allInfoStart)
        allInfoStartTextView.text = event!!.start

        val allInfoEndTextView: TextView = findViewById<TextView>(R.id.allInfoEnd)
        allInfoEndTextView.text = event!!.end


        val allInfoDescriptionTextView: TextView = findViewById<TextView>(R.id.allInfoDescription)
        allInfoDescriptionTextView.text = event!!.description

        val allInfoEventTypeTextView: TextView = findViewById<TextView>(R.id.allInfoEventType)
        allInfoEventTypeTextView.text = event!!.type

        val allInfoPeopleNeededTextView: TextView = findViewById<TextView>(R.id.allInfoPeopleNeeded)
        allInfoPeopleNeededTextView.text = event!!.reqPeople.toString()


        val peopleSubscribedView: TextView = findViewById<TextView>(R.id.peopleSubscribed)
        peopleSubscribedView.text = peopleSubscribed.toString()

        val allInfoSubscribe: TextView = findViewById<TextView>(R.id.Subscribe)
        if(subscribedOrNot)
            allInfoSubscribe.text = "unsubscribe"
        else
            allInfoSubscribe.text = "subscribe"

        if ((event!!.latitude=="0.0" && event!!.longitude=="0.0") || (event!!.latitude=="" && event!!.longitude=="")){
            SeeMapButton.text = "No Localisation!"
            SeeMapButton.setEnabled(false)
        }

        Subscribe.setOnClickListener {
            toggleSubscribeEvent(event!!).execute()
        }


        Chat.setOnClickListener {
            val intent = Intent(this, plusone.plusone.Chat.MainActivity::class.java)
            intent.putExtra("eventID", event!!.id)
            intent.putExtra("username", CurrentUser.username)
            startActivity(intent)
        }

        SeeMapButton.setOnClickListener{
            ///val gmmIntentUri = Uri.parse("geo:0,0?q="+allInfoLocation+"")
            ///val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            ///mapIntent.`package` = "com.google.android.apps.maps"
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("all","Justlikethat")
            intent.putExtra("latitudeGiven",event!!.latitude)
            intent.putExtra("longitudeGiven",event!!.longitude)
            startActivity(intent)

            /// startActivity(intent)
        }
    }


}
