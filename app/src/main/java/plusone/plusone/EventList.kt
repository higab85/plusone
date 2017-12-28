package plusone.plusone


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
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*


// This activity will show an undetailed list of all the events (filtered or not depending on
// whether a filter has been applied or not).
class EventList : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {



    private var eventsInfoList:List<Event> = emptyList()
    private var eventsInfoListCurated:List<Event> = emptyList()


    var myRecyclerView: RecyclerView? = null
    var eventListInfoAdapter: EventListInfoAdapter? = null

    val PERMISSON_REQUEST_CODE = 1001
    val PLAY_SERVICE_RESOLUTION_REQUEST = 1000
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    var latitudeDevice:Double = 0.0
    var longitudeDevice:Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        requestPermission()
        if(checkPlayService()){
            buildGoogleApiCLient()
        }

        val filterType = this.intent.getStringExtra("filter type")
        val filterSearch:String? = this.intent.getStringExtra("filter search")
        val filterOther:String? = this.intent.getStringExtra("filter other")

        var queryMap = mutableMapOf<String,String>()
        if (filterType != null)
            queryMap["type"] = filterType
        if(filterSearch != null)
            queryMap["name"] = filterSearch

        refreshEventData().execute(queryMap)


        if(filterOther=="EventSubscribed") {
            EventSubscriptionsFrom(CurrentUser).execute()
        }

        myRecyclerView = findViewById<RecyclerView>(R.id.event_list_recycler_view)
        myRecyclerView?.setHasFixedSize(true)
        myRecyclerView?.layoutManager = LinearLayoutManager(this@EventList)


        val searchButton: Button?  = findViewById<Button>(R.id.imageButtonSearch)
        val timeSortButton: Button?  = findViewById<Button>(R.id.sortTimeButton)
        val distanceSortButton: Button?  = findViewById<Button>(R.id.sortDistanceButton)
        val filterButton:Button? = findViewById<Button>(R.id.filterButton)


        fun curateInfoList(list:List<Event>){
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,list,latitudeDevice,longitudeDevice)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

        searchButton?.setOnClickListener{view->
            val searchBar = findViewById<EditText>(R.id.editTextSearch2)
            val searchWord:String = searchBar.text.toString()
            eventsInfoListCurated = LocalEventFilter.searchEventByName(eventsInfoList, searchWord)
            curateInfoList(eventsInfoListCurated)
            myRecyclerView?.adapter= eventListInfoAdapter
        }


        var orderTimeFirstToLast = false
        var orderDistanceClosestFirst = false

        fun orderEvents(order:Boolean, list:List<Event>){
            when(order){
                true -> eventsInfoList = list.reversed()
                false -> eventsInfoList = list
            }
            curateInfoList(eventsInfoList)
        }

        timeSortButton?.setOnClickListener{view->
            val list = LocalEventFilter.orderTimeFirstToLast(eventsInfoList)
            orderEvents(orderTimeFirstToLast, list)

            orderTimeFirstToLast = !orderTimeFirstToLast
            orderDistanceClosestFirst = false
        }

        distanceSortButton?.setOnClickListener{view->
            val list = LocalEventFilter.orderEventsByDistance(eventsInfoList)
            orderEvents(orderDistanceClosestFirst, list)

            orderDistanceClosestFirst = !orderDistanceClosestFirst
            orderTimeFirstToLast = false
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
        createLocationRequest()
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
    inner class refreshEventData: AsyncTask<Map<String,String>, Void, Boolean>() {

        override fun doInBackground(vararg params: Map<String,String>):Boolean{
            System.out.println("refreshing event data")
            eventsInfoList = ServerConnection.getEvents(params[0])!!
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
            myRecyclerView?.adapter= eventListInfoAdapter
        }
    }


    inner class EventSubscriptionsFrom(private val user:User):AsyncTask<User, Boolean, Boolean>(){

        override fun doInBackground(vararg params: User):Boolean? {
            eventsInfoList = ServerConnection.getEventSubscriptionsFrom(user)!!
            return true

        }

        override fun onPostExecute(success: Boolean?) {
            eventListInfoAdapter = EventListInfoAdapter(this@EventList,eventsInfoList,latitudeDevice,longitudeDevice)
            myRecyclerView?.adapter= eventListInfoAdapter
        }

    }

}