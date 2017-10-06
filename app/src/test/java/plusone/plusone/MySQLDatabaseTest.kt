package plusone.plusone

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by tyler on 6/10/17.
 */
class MySQLDatabaseTest{

    var db = MySQLDatabase

    // tests whether the app can connect to DB
    @Test
//    @Throws(Exception::class)
    fun canConnectToDB() {
        assertTrue(db.getConnection())
    }

    // tests whether user with correct credentials can be verified
    @Test
    fun userAuthCorrectCredentials(){
        db.getConnection()
        assertTrue(db.verifyUser("Allan", "soixanteneuf"))
//        assertFalse(db.verifyUser("Allan", "wrongpass"))
    }
    // tests whether user with wrong password can be verified
    @Test
    fun userAuthWrongPassword(){
        db.getConnection()
        assertFalse(db.verifyUser("Allan", "wrongpass"))
    }

    // tests whether unregistered can be verified
    @Test
    fun userAuthWrongUser(){
        db.getConnection()
        assertFalse(db.verifyUser("McNuggets", "Kluckpass"))
    }
}