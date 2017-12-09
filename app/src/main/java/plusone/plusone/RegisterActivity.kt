package plusone.plusone

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        registerButton2.setOnClickListener{

            val newUser = User()

            newUser.name = name.text.toString()
            newUser.username = userName.text.toString()
            newUser.password = password2.text.toString()
            newUser.email = emailRegister.text.toString()

            RegisterUser().execute(newUser)

        }



    }

    inner class RegisterUser: AsyncTask<User, Void, Boolean>() {

        override fun doInBackground(vararg params: User): Boolean {
            return ServerConnection.registerUser(params[0])
        }

        override fun onPostExecute(success: Boolean?) {
            finish()
        }

        override fun onCancelled() {
        }
    }
}
