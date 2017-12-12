/*
package plusone.plusone

import kotlinx.android.synthetic.main.activity_event.*
import org.junit.Assert.*
import org.junit.Test
import java.sql.SQLException
import java.time.LocalDateTime
import kotlin.test.*
*
 * Created by tyler on 6/10/17.


class DatabaseConnectionTest {


    // tests whether the app can connect to DB
    @Test
    fun canConnectToDB() {
        assertTrue(DatabaseConnection.conn != null)
    }

    // tests whether user with correct credentials can be verified
    @Test
    fun userAuthCorrectCredentials(){
        assertTrue(DatabaseConnection.verifyUser("demo", "demo"))
//        assertFalse(db.verifyUser("Allan", "wrongpass"))
    }
    // tests whether user with wrong password can be verified
    @Test
    fun userAuthWrongPassword(){
        assertFalse(DatabaseConnection.verifyUser("demo", "wrongpass"))
    }

    // tests whether unregistered can be verified
    @Test
    fun userAuthWrongUser(){
        assertFalse(DatabaseConnection.verifyUser("McNuggets", "Kluckpass"))
    }

    // tests whether user object is created
    @Test
    fun userObjectCreation(){
        DatabaseConnection.loginUser("demo", "demo")
        assertTrue(CurrentUser.userLoggedIn)
    }

    // test whether events can be saved to db
    @Test
    fun eventCreation(){
        DatabaseConnection.loginUser("demo", "demo")
        var event = Event()

        event.name = "test-title"
        event.start = "2017-12-03T13:12"
        event.end = "2017-12-03T14:12"
        event.location = "club"
        event.type = "OTHER"
        event.reqPeople = 1
        try {
            DatabaseConnection.createEventDB(event)
        }catch (e:SQLException) {
            fail()
        }
    }

    @Test
    fun getEvents(){
        DatabaseConnection.loginUser("demo", "demo")
        assertNotNull(DatabaseConnection.getEventsDB())
    }

    @Test
    @Throws(NumberFormatException:: class)
    fun usersuscritoNoPuedeTenerString(){
        DatabaseConnection.loginUser("demo", "demo")
        val input1: String = "juanan"
        assertFailsWith(NumberFormatException:: class) { DatabaseConnection.subscribeEventDB(1,input1.toInt()) }
    }

    @Test
    @Throws(NumberFormatException:: class)
    fun userunsuscribeNoPuedeTenerString(){
        DatabaseConnection.loginUser("demo", "demo")
        val input1: String = "juanan"
        assertFailsWith(NumberFormatException:: class) { DatabaseConnection.dessubscribeEventDB(1,input1.toInt()) }
    }
    @Test
    fun usersuscribirNoNull(){
        DatabaseConnection.loginUser("demo", "demo")
        assertNotNull(DatabaseConnection.subscribeEventDB(idEvent=0, idUser = 1))

    }
    @Test
    fun userdesuscribirNoNull(){
        DatabaseConnection.loginUser("demo", "demo")
        assertNotNull(DatabaseConnection.dessubscribeEventDB(idEvent = 0, idUser = 1))

    }
    @Test
    fun userSinParametrosdesuscribirNoNull(){
        DatabaseConnection.loginUser("demo", "demo")
        assertNotNull(DatabaseConnection.dessubscribeEventDB(0, 1))

    }
    @Test
    fun getEventAñadidoCampoIdParLuegoUsarEseIdEnElSuscribirYDesuscribir(){
        DatabaseConnection.loginUser("demo", "demo")
        var event = Event()

        event.name = "test-title"
        event.start = "02/04/2017 at 02:00"
        event.end = "02/04/2017 at 02:30"
        event.location = "club"
        event.type = "OTHER"
        event.reqPeople = 1
        event.eventID = 1
        try {
            DatabaseConnection.getEventsDB()
        }catch (e:SQLException) {
            fail()
        }
    }

    @Test
    fun searchEventAñadidoCampoIdParLuegoUsarEseIdEnElSuscribirYDesuscribir(){
        DatabaseConnection.loginUser("demo", "demo")
        var event = Event()

        event.name = "test-title"
        event.start = "02/04/2017 at 02:00"
        event.end = "02/04/2017 at 02:30"
        event.location = "club"
        event.type = "OTHER"
        event.reqPeople = 1
        event.eventID = 1
        try {
            DatabaseConnection.searchEventsDB(toString())
        }catch (e:SQLException) {
            fail()
        }
    }
    */
/*
    @Test
    fun comprobarFuncionamientoFuncionSuscribeEvent(){
        DatabaseConnection.loginUser("demo", "demo")
        try {
            DatabaseConnection.subscribeEventDB(idEvent = 0,idUser = 0)
        }catch (e:SQLException) {
            fail()
        }
    }
    @Test
    fun comprobarFuncionamientoFuncionUnSuscribeEvent(){
        DatabaseConnection.loginUser("demo", "demo")
        try {
            DatabaseConnection.dessubscribeEventDB(idEvent = 0,idUser = 0)
        }catch (e:SQLException) {
            fail()
        }
    }
    @Test

    fun comprobarFuncionamientoFuncionSuscribeVerdaderoSiLePaso2Numeros(){

        DatabaseConnection.loginUser("demo", "demo")
        try {
            assertTrue(DatabaseConnection.subscribeEventDB(1,2)
            )
        }catch (e:SQLException) {
            fail()
        }
    }
    @Test

    fun comprobarFuncionamientoFuncionUnSuscribeVerdaderoSiLePaso2Numeros(){

        DatabaseConnection.loginUser("demo", "demo")
        try {
            assertTrue(DatabaseConnection.dessubscribeEventDB(1,2)
            )
        }catch (e:SQLException) {
            fail()
        }
    }


}
*/
