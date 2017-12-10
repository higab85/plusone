package plusone.plusone

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Button
import android.widget.EditText


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageButtonSettings = findViewById<ImageButton>(R.id.imageButtonSettings)
        val imageButtonAddEvent = findViewById<ImageButton>(R.id.imageButtonAddEvent)
        val imageButtonSearchHome = findViewById<ImageButton>(R.id.imageButtonSearchHome)

        val buttonSports = findViewById<Button>(R.id.buttonSportsEvents)
        val buttonFood = findViewById<Button>(R.id.buttonFood)
        val buttonParty = findViewById<Button>(R.id.buttonParty)
        val buttonEntertainment = findViewById<Button>(R.id.buttonEntertainment)
        val buttonLearning = findViewById<Button>(R.id.buttonLearning)
        val buttonOthers = findViewById<Button>(R.id.buttonOthers)

        imageButtonSettings.setOnClickListener{
            Toast.makeText(this,"Testing",Toast.LENGTH_SHORT).show()
        }
        imageButtonAddEvent.setOnClickListener{view->
            val intent = Intent(this, EventCreateActivity::class.java)
            startActivity(intent)
        }
        imageButtonSettings.setOnClickListener{view->
            ///Toast.makeText(this,"Testing",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)

        }
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

        imageButtonSearchHome.setOnClickListener{
            val intent = Intent(this, EventList::class.java)
            val searchBar: EditText = findViewById<EditText>(R.id.editTextSearchHome)
            val searchWordHome:String = searchBar.text.toString()
            intent.putExtra("searchHome", searchWordHome)
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent)
        }
    }



}


