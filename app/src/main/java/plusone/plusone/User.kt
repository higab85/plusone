package plusone.plusone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

class User : Serializable {
    var name:String = ""
    var email:String = ""
    var username:String = ""
    var password:String = ""
    val public_id = ""



    constructor()

    constructor(name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(name: String, email: String, username: String, password: String) {
        this.name = name
        this.email = email
        this.username = username
        this.password = password
    }
}
