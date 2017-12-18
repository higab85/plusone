package plusone.plusone

import android.media.session.MediaSession

/**
 * Created by Gabriel on 6/10/17.
 */

object CurrentUser : User() {
    var userLoggedIn:Boolean = false
    var token:String = ""
    var id:String = ""
}