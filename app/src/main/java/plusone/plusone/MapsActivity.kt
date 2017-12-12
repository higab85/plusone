package plusone.plusone

import android.content.Intent
import android.support.v4.app.FragmentActivity
/**import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions*/

/**class MapsActivity : FragmentActivity(), OnMapReadyCallback {

private var mMap: GoogleMap? = null
var latitude =this.intent.getStringExtra("latitude")
var longitude =this.intent.getStringExtra("longitude")
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_maps)
// Obtain the SupportMapFragment and get notified when the map is ready to be used.

val mapFragment = supportFragmentManager
.findFragmentById(R.id.map) as SupportMapFragment
mapFragment.getMapAsync(this)
}


/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
*/
override fun onMapReady(googleMap: GoogleMap) {
mMap = googleMap
latitude= ("-34.0")
longitude=("151.0")
// Add a marker in Sydney and move the camera
val sydney = LatLng(latitude.toDouble(),longitude.toDouble())
val zoom=16f
mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoom))
}
}
 */

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageButton

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import java.io.IOException
import android.location.Address;
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_event.*
import plusone.plusone.R.id.linear

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps)
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val name =this.intent.getStringExtra("name")
        val description =this.intent.getStringExtra("description")
        val start =this.intent.getStringExtra("start")
        val end =this.intent.getStringExtra("end")
        val reqPeople =this.intent.getStringExtra("reqPeople")
        val addressKey =this.intent.getStringExtra("address")

        val pageAllInfo = this.intent.getStringExtra("all")



        val searchBar: EditText = findViewById<EditText>(R.id.SearchAddress) as EditText
        val linear:LinearLayout = findViewById<LinearLayout>(R.id.linear) as LinearLayout

        if (pageAllInfo!=null && pageAllInfo!=""){
            linear.setVisibility(View.GONE)
        }

        searchBar.setText(addressKey)



    }



    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {


        var addressList:List<Address>? = null

        val SearchMapButton =findViewById<ImageButton>(R.id.SearchMapButton) as ImageButton
        var marker:Marker? = null

        var addressTo: String = ""
        var latitudeTo: Double= 0.0
        var longitudeTo: Double =0.0

        val pageAllInfo = this.intent.getStringExtra("all")

        googleMap.uiSettings.setZoomControlsEnabled(true);

        if (pageAllInfo!=null && pageAllInfo!=""){
            var latitudeGiven = this.intent.getStringExtra("latitudeGiven")
            var longitudeGiven = this.intent.getStringExtra("longitudeGiven")
            var latLngGiven: LatLng = LatLng(latitudeGiven.toDouble(),longitudeGiven.toDouble())

            marker = googleMap.addMarker(MarkerOptions().position(latLngGiven)
                    .title("Your Event Location").draggable(true).snippet("Latitude: "+latitudeGiven.toDouble()+"/ Longitude: "+longitudeGiven.toDouble()))
            val zoomLevel = 16.0f
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngGiven, zoomLevel))
            Toast.makeText(this, "Click on Marker to return to the previous page!",
                    Toast.LENGTH_LONG).show();
        }

        SearchMapButton.setOnClickListener{view->
            val searchBar2: EditText = findViewById<EditText>(R.id.SearchAddress) as EditText
            val SearchAddress:String = searchBar2.text.toString()



            if(SearchAddress!=null || SearchAddress!=""){
                val coder = Geocoder(this)
                try{
                    addressList = coder.getFromLocationName(SearchAddress,1)
                }catch (e: IOException){
                    e.printStackTrace()
                }

                var addressList2:List<Address> = addressList.orEmpty()
                var address:Address = addressList2[0]


                var latLng: LatLng = LatLng(address.latitude, address.longitude)
                latitudeTo = address.latitude
                longitudeTo = address.longitude

                if (marker!=null){
                    googleMap.clear()
                }
                marker = googleMap.addMarker(MarkerOptions().position(latLng)
                        .title("Your Event Location :"+SearchAddress).draggable(true).snippet("Latitude: "+address.latitude+"/ Longitude: "+address.longitude))
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                val zoomLevel = 16.0f
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
                Toast.makeText(this, "Click on Marker to add it as a position!",
                        Toast.LENGTH_LONG).show();
                addressTo = SearchAddress
            }
        }

        googleMap.setOnInfoWindowClickListener{

            if (pageAllInfo!=null && pageAllInfo!=""){
                super.onBackPressed();
            }
            else{
               /** val name =this.intent.getStringExtra("name")
                val description =this.intent.getStringExtra("description")
                val start =this.intent.getStringExtra("start")
                val end =this.intent.getStringExtra("end")
                val address = addressTo

                val intent = Intent(this, EventCreateActivity::class.java)
                intent.putExtra("name",name )
                intent.putExtra("description",description)
                intent.putExtra("start",start )
                intent.putExtra("end",end )
                intent.putExtra("address", address)
                intent.putExtra("latitude", latitudeTo)
                intent.putExtra("longitude", longitudeTo)
                startActivity(intent)**/
                val intent = Intent()
                intent.putExtra("latitude", latitudeTo)
                intent.putExtra("longitude", longitudeTo)
                setResult(RESULT_OK, intent);
                finish()

            }

        }


    }
}

