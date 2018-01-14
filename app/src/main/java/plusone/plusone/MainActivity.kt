package plusone.plusone


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.TextView
import com.mysql.fabric.Server
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var newDB = false
    var toggleDB:Switch? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        val toggle = object: ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){


            /** Called when a drawer has settled in a completely open state.  */
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()

                toggleDB = findViewById<Switch>(R.id.database_switch)
                if(ServerConnection.urlHostState)
                    toggleDB?.toggle()
                toggleDB?.setOnCheckedChangeListener { buttonView, isChecked ->
                    ChangeDB().execute(isChecked)
                }
            }
        }

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

        fun eventListFilter(intentKey:String, intentValue:String){
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            intent.putExtra(intentKey, intentValue)
            startActivity(intent)
        }

        buttonSports.setOnClickListener{
            eventListFilter("filter type", "Sports Event")
        }
        buttonParty.setOnClickListener{
            eventListFilter("filter type", "Party")
        }
        buttonEntertainment.setOnClickListener{
            eventListFilter("filter type", "Entertainment")
        }
        buttonLearning.setOnClickListener{
            eventListFilter("filter type", "Learning")
        }
        buttonOthers.setOnClickListener{
            eventListFilter("filter type", "Others")
        }
        buttonFood.setOnClickListener{
            eventListFilter("filter type", "Food")
        }
        imageButtonSearchHome.setOnClickListener {
            val searchBar: EditText = findViewById<EditText>(R.id.editTextSearchHome)
            val searchWordHome: String = searchBar.text.toString()
            eventListFilter("filter search", searchWordHome)
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
        else if (id == R.id.imageButtonSettings) {
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)
        }
        else if (id == R.id.buttonEventsSubscribed) {
            val intent = Intent(this, EventList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            intent.putExtra("filter other", "EventSubscribed")
            startActivity(intent)
        }


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    inner class ChangeDB: AsyncTask<Boolean, Boolean, Boolean>() {

        override fun doInBackground(vararg isChecked: Boolean?): Boolean {
            ServerConnection.toggleDB(isChecked[0]!!)
            if(!ServerConnection.loginUser()) {
                ServerConnection.toggleDB(!isChecked[0]!!)
                return false
            }
            return true
        }

        override fun onPostExecute(success: Boolean) {
            if (success)
                newDB = true
            if(newDB) {
                toast("Switched database!")
                newDB=false
            }
            else {
                toast("Account doesn't exist in other DB!")
                toggleDB?.toggle()
            }
        }
    }
}


