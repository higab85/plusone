package plusone.plusone

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageButtonSettings =findViewById(R.id.imageButtonSettings) as ImageButton
        val imageButtonAddEvent =findViewById(R.id.imageButtonAddEvent) as ImageButton
        val buttonSports = findViewById(R.id.buttonSportsEvents) as  Button
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
            val intent = Intent(this, CardView::class.java)
            startActivity(intent)
        }

    }

}
