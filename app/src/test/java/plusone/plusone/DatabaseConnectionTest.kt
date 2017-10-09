package plusone.plusone

import org.junit.Assert.*
import org.junit.Test
import java.sql.SQLException

/**
 * Created by tyler on 6/10/17.
 */
class DatabaseConnectionTest {


    // tests whether the app can connect to DB
    @Test
    fun canConnectToDB() {
        assertTrue(DatabaseConnection.conn!! != null)
    }

    // tests whether user with correct credentials can be verified
    @Test
    fun userAuthCorrectCredentials(){
        assertTrue(DatabaseConnection.verifyUser("demo-user", "demo"))
//        assertFalse(db.verifyUser("Allan", "wrongpass"))
    }
    // tests whether user with wrong password can be verified
    @Test
    fun userAuthWrongPassword(){
        assertFalse(DatabaseConnection.verifyUser("demo-user", "wrongpass"))
    }

    // tests whether unregistered can be verified
    @Test
    fun userAuthWrongUser(){
        assertFalse(DatabaseConnection.verifyUser("McNuggets", "Kluckpass"))
    }

    // tests whether user object is created
    @Test
    fun userObjectCreation(){
        DatabaseConnection.loginUser("demo-user", "demo")
        assertTrue(CurrentUser.userLoggedIn)
    }

}