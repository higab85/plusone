package plusone.plusone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton


class AjustesActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        ///Make the edittext non editable
        val myEditTextNombre = findViewById(R.id.editTextAjustesNombre) as EditText
        myEditTextNombre.isEnabled = false

        ///Make the edittext non editable
        val myEditTextApellido = findViewById(R.id.editTextAjustesAppel) as EditText
        myEditTextApellido.isEnabled = false

        ///Make the edittext non editable
        val myEditTextEmail = findViewById(R.id.editTextAjustesEmail) as EditText
        myEditTextEmail.isEnabled = false

        ///Make the edittext non editable
        val myEditTextContrasena = findViewById(R.id.editTextAjustesContrasena) as EditText
        myEditTextContrasena.isEnabled = false

        ///Get the 4 ImageButton
        var ButtonNombre: ImageButton?  = findViewById(R.id.imageButtonNombre) as ImageButton
        var ButtonApellido: ImageButton?  = findViewById(R.id.imageButtonApellido) as ImageButton
        var ButtonEmail: ImageButton?  = findViewById(R.id.imageButtonEmail) as ImageButton
        var ButtonContrasena: ImageButton?  = findViewById(R.id.imageButtonContrasena) as ImageButton

        ///Make the edittext editable after click on ImageButton
        if (ButtonNombre != null){
            ButtonNombre.setOnClickListener {
                myEditTextNombre.isEnabled= true
            }
        }
        ///Make the edittext editable after click on ImageButton
        if (ButtonApellido != null){
            ButtonApellido.setOnClickListener {
                myEditTextApellido.isEnabled= true
            }
        }
        ///Make the edittext editable after click on ImageButton
        if (ButtonEmail != null){
            ButtonEmail.setOnClickListener {
                myEditTextEmail.isEnabled= true
            }
        }
        ///Make the edittext editable after click on ImageButton
        if (ButtonContrasena!= null){
            ButtonContrasena.setOnClickListener {
                myEditTextContrasena.isEnabled= true
            }
        }



    }
}