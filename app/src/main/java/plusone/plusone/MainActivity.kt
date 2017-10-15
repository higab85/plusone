package plusone.plusone

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageButtonSettings =findViewById(R.id.imageButtonSettings) as ImageButton
        val imageButtonAddEvent =findViewById(R.id.imageButtonAddEvent) as ImageButton

        imageButtonSettings.setOnClickListener{view->
            ///Toast.makeText(this,"Testing",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)

        }
        imageButtonAddEvent.setOnClickListener{view->
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }

    }

}
