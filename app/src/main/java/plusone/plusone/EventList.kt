package plusone.plusone

import android.Manifest
import android.app.DatePickerDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.app.SearchManager
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.view.View
import android.widget.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.support.design.widget.NavigationView
import android.support.v7.widget.CardView
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_all_info_event.*
import plusone.plusone.R.id.editTextSearch2
import java.lang.Math.*


// This activity will show an undetailed list of all the events (filtered or not depending on
// whether a filter has been applied or not).
class EventList : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {



    private var eventsInfoList:List<Event>? = null
    private var eventsInfoListCurated:List<Event>? = null


    var myRecyclerView: RecyclerView? = null
    var eventListInfoAdapter: EventListInfoAdapter? = null
    val searchWord:String = ""

    val PERMISSON_REQUEST_CODE = 1001
    val PLAY_SERVICE_RESOLUTION_REQUEST = 1000
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    var locationDevice: Location? = null
    var latitudeDevice:Double = 0.0
    var longitudeDevice:Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        requestPermission()
        if(checkPlayService()){
            buildGoogleApiCLient()
        }

        val searchHome = this.intent.getStringExtra("searchHome")
        if (searchHome==""){
            refreshEventData().execute()
        }
        else if (searchHome=="Sports Event"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Food"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Party"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Entertainment"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Learning"){
            refreshEventByType(searchHome).execute()
        }
        else if (searchHome=="Others"){
            refreshEventByType(searchHome).execute()
        }else if(searchHome=="EventSubscribed") {
            var user:User = CurrentUser
            EventSubscriptionsFrom(user).execute()
        } else if (searchHome=="user_id"){
            val user_id = this.intent.getStringExtra("user_id")
            refreshMyEvent(user_id).execute()
        }
        else{
            refreshSearchEventData(searchHome).execute()
        }

        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_view)
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventList)


        val searchButton: Button?  = findViewById<Button>(R.id.imageButtonSearch)
        val timeSortButton: Button?  = findViewById<Button>(R.id.sortTimeButton)
        val distanceSortButton: Button?  = findViewById<Button>(R.id.sortDistanceButton)
        val filterButton:Button? = findViewById<Button>(R.id.filterButton)




        if (searchButton != null){
            searchButton.setOnClickListener{view->
                val searchBar: EditText = findViewById<EditText>(R.id.editTextSearch2)
                val searchWord:String = searchBar.text.toString()
                eventsInfoListCurated = LocalEventFilter.searchEventByName(eventsInfoList, searchWord)
                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoListCurated,latitudeDevice,longitudeDevice)
                myRecyclerView?.adapter= eventListInfoAdapter
            }
        }

        if (timeSortButton != null){
            timeSortButton.setOnClickListener{view->
                eventsInfoList = LocalEventFilter.orderTimeFirstToLast(eventsInfoList!!)
                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
                myRecyclerView?.adapter= eventListInfoAdapter
            }
        }

        if (distanceSortButton != null){
            distanceSortButton.setOnClickListener{view->
                eventsInfoList = LocalEventFilter.orderEventsByDistance(eventsInfoList!!)
                eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
                myRecyclerView?.adapter= eventListInfoAdapter
            }
        }


    }



    private fun requestPermission() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSON_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            PERMISSON_REQUEST_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(checkPlayService()){
                        buildGoogleApiCLient()
                    }
                }
            }
        }

    }

    private fun buildGoogleApiCLient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
    }

    private fun checkPlayService(): Boolean {

        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RESOLUTION_REQUEST).show()
            }
            else{
                Toast.makeText(applicationContext,"This device is not supported", Toast.LENGTH_LONG).show()
                finish()
            }
            return false
        }
        return true
    }

    override fun onConnected(p0: Bundle?) {
        createLocationRequest();
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000
        mLocationRequest!!.fastestInterval = 5000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this)
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("ERROR", "Connection Failed :"+p0.errorCode)
    }

    override fun onLocationChanged(p0: Location?) {
        //Toast.makeText(applicationContext,"${p0!!.latitude} - ${p0!!.longitude}", Toast.LENGTH_LONG).show()

        latitudeDevice = p0!!.latitude
        longitudeDevice = p0!!.longitude

    }

    override fun onConnectionSuspended(p0: Int) {
       mGoogleApiClient!!.connect()
    }

    override fun onStart() {
        super.onStart()
        if(mGoogleApiClient!=null){
            mGoogleApiClient!!.connect()
        }
    }

    override fun onDestroy() {
        mGoogleApiClient!!.disconnect()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        checkPlayService()
    }




    fun funTime(view: View, date:String){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_view)
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventList)

        fun singleDigitParser(value:Int):String {
            if (value < 10)
                return "0$value"
            return value.toString()
        }
        val dpd = TimePickerDialog(this, android.R.style.Animation_Dialog,
                TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->

                    val time = LocalDateTime.parse(date + "T${singleDigitParser(hour)}:${singleDigitParser(minute)}", DateTimeFormatter.ISO_DATE_TIME)

                    eventsInfoListCurated = LocalEventFilter.filterByStartTimeAfter(eventsInfoList!!, time)
                    eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoListCurated,latitudeDevice,longitudeDevice)
                    myRecyclerView?.adapter= eventListInfoAdapter
                },hour, minute, true)

        // show timepicker
        dpd.show()
    }


    fun funDate(view: View){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val filterButton:Button? = findViewById<Button>(R.id.filterButton)
        var returnDate:String? = null

        fun singleDigitParser(value:Int):String{
            if(value < 10)
                return "0$value"
            return value.toString()
        }

        val dpd = DatePickerDialog(this, android.R.style.Animation_Dialog,
                DatePickerDialog.OnDateSetListener{ datePicker, year, monthOfYear, dayOfMonth ->
                    // MonthOfYear + 1 because january is month 0
                        funTime(view, "$year-${singleDigitParser(monthOfYear + 1)}-${singleDigitParser(dayOfMonth)}")

                }, year, month, day)

        // show datepicker
        dpd.show()

    }


    override fun onNewIntent(intent:Intent) {
        handleIntent(intent)
    }


     private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
        }
    }


    // refresh the list: Synchronises local list of events with database on cloud.
    inner class refreshEventData: AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void):Boolean{
            eventsInfoList = ServerConnection.getEvents()
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            myRecyclerView?.adapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
        }

        override fun onCancelled() {
        }
    }

    // updates the list to only show events which are relevant to search query
    inner class refreshSearchEventData(private val searchObject:String): AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg params: String):Boolean{
            val query = mapOf("name" to searchObject)
            eventsInfoList = ServerConnection.getEvents(query)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        override fun onCancelled() {
        }
    }

    // updates the list to only show events which are relevant to search query
    inner class refreshEventByType(private val type:String): AsyncTask<String, Void, Boolean>() {

        override fun doInBackground(vararg params: String):Boolean{
            val query = mapOf("type" to type)
            eventsInfoList = ServerConnection.getEvents(query)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        override fun onCancelled() {
        }
    }


    inner class refreshMyEvent(private val user_id:String): AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg params: String):Boolean{
            val query = mapOf("user_id" to user_id)
            eventsInfoList = ServerConnection.getEvents(query)
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        override fun onCancelled() {
        }
    }


    inner class EventSubscriptionsFrom(private val user:User):AsyncTask<User, Boolean, Boolean>(){

        override fun doInBackground(vararg params: User):Boolean? {

            eventsInfoList = ServerConnection.getEventSubscriptionsFrom(user)
            return true

        }

        override fun onPostExecute(success: Boolean?) {

        }

        override fun onCancelled() {
        }



    }

    ///Code to take the location of the device

   }