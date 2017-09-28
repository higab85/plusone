package plusone.plusone
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amazonaws.mobile.auth.ui.SignInActivity
import kotlinx.android.synthetic.main.activity_register.*



class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // Set up the login form.


        registerButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

}



