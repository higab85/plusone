package plusone.plusone

import kotlinx.android.synthetic.main.activity_event.*
import org.junit.Assert.*
import org.junit.Test
import java.sql.SQLException
import java.time.LocalDateTime
import kotlin.test.*
/**
 * Created by tyler on 6/10/17.
 */
class ServerConnectionTest {



    // tests whether user with correct credentials can be verified
    @Test
    fun userAuthCorrectCredentials(){
        CurrentUser.email = "test@test.com"
        CurrentUser.password = "test"
        assertTrue(ServerConnection.loginUser())
        System.out.println(CurrentUser.token)

//        assertFalse(db.verifyUser("Allan", "wrongpass"))
    }
    // tests whether user with wrong password can be verified
    @Test
    fun userAuthWrongPassword(){
        CurrentUser.email = "test@test.com"
        CurrentUser.password = "1234"
        assertFalse(ServerConnection.loginUser())
    }

    // tests whether unregistered can be verified
    @Test
    fun userAuthWrongUser(){
        CurrentUser.email = "inexistent-user@test.com"
        CurrentUser.password = "1234"
        assertFalse(ServerConnection.loginUser())
    }

    // tests whether user object is created
    @Test
    fun userObjectCreation(){
        CurrentUser.email = "test@test.com"
        CurrentUser.password = "test"
        ServerConnection.loginUser()
        assertTrue(CurrentUser.token != "")
    }

    // test whether events can be saved to db
    @Test
    fun eventCreation(){
        CurrentUser.email = "test@test.com"
        CurrentUser.password = "test"
        ServerConnection.loginUser()
        var event = Event()

        event.name = "test-title"
        event.start = "2017-12-03T13:12"
        event.end = "2017-12-03T14:12"
        event.latitude = "12341324"
        event.location = "club"
        event.type = "OTHER"
        event.reqPeople = 1

        assertTrue(ServerConnection.createEvent(event))
    }

    @Test
    fun getEvents(){
        CurrentUser.email = "test@test.com"
        CurrentUser.password = "test"
        ServerConnection.loginUser()
        assertNotNull(ServerConnection.getEvents())
    }

}