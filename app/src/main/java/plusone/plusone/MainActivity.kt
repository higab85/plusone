package plusone.plusone

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.TextView




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val header = navigationView.getHeaderView(0)

        val navName = header.findViewById<TextView>(R.id.navName) as TextView
        navName.setText(CurrentUser.name)

        val navEmail = header.findViewById<TextView>(R.id.navEmail) as TextView
        navEmail.setText(CurrentUser.email)


        val imageButtonSearchHome = findViewById<ImageButton>(R.id.imageButtonSearchHome)


        val buttonSports = findViewById<Button>(R.id.buttonSportsEvents)
        val buttonFood = findViewById<Button>(R.id.buttonFood)
        val buttonParty = findViewById<Button>(R.id.buttonParty)
        val buttonEntertainment = findViewById<Button>(R.id.buttonEntertainment)
        val buttonLearning = findViewById<Button>(R.id.buttonLearning)
        val buttonOthers = findViewById<Button>(R.id.buttonOthers)
//        val buttonEventsSubscribed = findViewById<Button>(R.id.buttonEventsSubscribed)


        buttonSports.setOnClickListener{
           val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "Sports Event")
            startActivity(intent)
        }
        buttonParty.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "Party")
            startActivity(intent)
        }
        buttonEntertainment.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "Entertainment")
            startActivity(intent)
        }
        buttonLearning.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "Learning")
            startActivity(intent)
        }
        buttonOthers.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "Others")
            startActivity(intent)
        }
        buttonFood.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "Food")
            startActivity(intent)
        }
//        buttonEventsSubscribed.setOnClickListener {
//            val intent = Intent(this, EventList::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//            intent.putExtra("searchHome", "EventSubscribed")
//            startActivity(intent)
//
//
//        }
        imageButtonSearchHome.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            val searchBar: EditText = findViewById<EditText>(R.id.editTextSearchHome)
            val searchWordHome:String = searchBar.text.toString()
            intent.putExtra("searchHome", searchWordHome)
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.imageButtonAddEvent) {
            val intent = Intent(this, EventCreateActivity::class.java)
            intent.putExtra("activateEdit","isFalse")
            startActivity(intent)
        }
        else if (id == R.id.imageButtonMyEvents) {
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("searchHome", "user_id")
            intent.putExtra("user_id", CurrentUser.id)
            startActivity(intent)
        }
        else if (id == R.id.imageButtonSettings) {
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }



}


