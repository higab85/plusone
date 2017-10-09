package plusone.plusone

/**
 * Created by Gabriel on 6/10/17.
 */

import java.sql.*
import java.util.Properties


object DatabaseConnection {

    // Constructor
    init{
        getConnection()
    }

    internal var conn: Connection? = null // connection to db

    // log user in. This will create a CurrentUser(static) with the user that has logged in
    fun loginUser(user:String, pass:String):Boolean{
        if (verifyUser(user, pass)) {
            var stmt: Statement? = null
            var resultset: ResultSet? = null
            try{
                print("misterblister $conn")
                stmt = conn!!.createStatement()
                var query = "SELECT * FROM plusone.users WHERE username = '$user';"
                resultset = stmt!!.executeQuery(query)
                resultset!!.next()
                CurrentUser.email = resultset!!.getString("email")
                CurrentUser.name  = resultset!!.getString("name")
                CurrentUser.userLoggedIn = true
                CurrentUser.username = user
                return true
            }
            catch (ex: SQLException){
                ex.printStackTrace()
            }
        }
        return false
    }

    // Returns true if password matches user on server.
    fun verifyUser(user:String, pass:String): Boolean {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        try{
            stmt = conn!!.createStatement()
            var query = "SELECT * FROM plusone.users WHERE username = '$user';"
            resultset = stmt!!.executeQuery(query)
            resultset!!.next()
            try {
                return pass == resultset.getString("pw")
            }
            catch (ex:SQLException) {
                // TODO: log this
                ex.printStackTrace()
            }
        }
        catch (ex: SQLException){
            // TODO: log this
            ex.printStackTrace()
        }
        return false
    }
    // TODO: registers user
    fun registerUser(user:String, pass:String){

    }


    /**
     * This method makes a connection to MySQL Server and initialises conn.
     */
    private fun getConnection():Boolean {
        val connectionProps = Properties()
        connectionProps.put("user", "plusone")
        connectionProps.put("password", "plusone")
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            "plusoneapp.ddns.net" +
                            ":" + "3306" + "/" +
                            "",
                    connectionProps)
            return true
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
        return false
    }


}