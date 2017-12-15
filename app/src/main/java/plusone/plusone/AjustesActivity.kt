package plusone.plusone

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import plusone.plusone.R.id.editTextSettingsPassword


class AjustesActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        val imageButtonAjustesEmail = findViewById<ImageButton>(R.id.imageButtonAjustesEmail)
        val imageButtonAjustesPassword = findViewById<ImageButton>(R.id.imageButtonAjustesPassword)
        val settingsChangeButton = findViewById<Button>(R.id.settingsChangeButton)


        val myEditTextEmail = findViewById<EditText>(R.id.editTextAjustesEmail) as EditText
        myEditTextEmail.isEnabled = false

        val myEditTextPassword = findViewById<EditText>(R.id.editTextSettingsPassword) as EditText
        myEditTextPassword.isEnabled = false

        myEditTextEmail.setText(CurrentUser.email)
        myEditTextPassword.setText(CurrentUser.password)

        if (imageButtonAjustesEmail != null){
            imageButtonAjustesEmail.setOnClickListener {
                myEditTextEmail.isEnabled= true
            }
        }

        if (imageButtonAjustesPassword != null){
            imageButtonAjustesPassword.setOnClickListener {
                myEditTextPassword.isEnabled= true
            }
        }

        if (settingsChangeButton != null){
            settingsChangeButton.setOnClickListener {
                val changeUser = User()
                changeUser.email = myEditTextEmail.text.toString()
                changeUser.password = myEditTextPassword.text.toString()
                changeUser.name = CurrentUser.name
                changeUser.username = CurrentUser.username

                ChangeUser().execute(changeUser)
                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(applicationContext,"User informations changed", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }

    }

    inner class ChangeUser: AsyncTask<User, Void, Boolean>() {

        override fun doInBackground(vararg params: User): Boolean {
            return ServerConnection.modifyUser(params[0])
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }
}