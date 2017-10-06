package plusone.plusone

/**
 * Created by Gabriel on 6/10/17.
 */

import java.sql.*
import java.util.Properties


object MySQLDatabase {

    internal var conn: Connection? = null
    internal var username = "plusone" // provide the username
    internal var password = "plusone" // provide the corresponding password

//    @JvmStatic fun main(args: Array<String>) {
//        // make a connection to MySQL Server
//        getConnection()
//        // execute the query via connection object
//        executeMySQLQuery()
//    }

    // verifies user, and password.
    fun verifyUser(user:String, pass:String): Boolean {
        var stmt: Statement? = null
        var resultset: ResultSet? = null
        try{
            stmt = conn!!.createStatement()
            var query = "SELECT * FROM plusone.users WHERE username = 'Allan';"
            resultset = stmt!!.executeQuery(query)
            resultset!!.next()
            if (pass == resultset!!.getString("pw"))
                return true
        }
        catch (ex: SQLException){
            ex.printStackTrace()
        }
        return false
    }
    // registers user
    fun registerUser(user:String, pass:String){

    }
    fun executeMySQLQuery() {
        var stmt: Statement? = null
        var resultset: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SHOW DATABASES;")

            if (stmt.execute("SHOW DATABASES;")) {
                resultset = stmt.resultSet
            }

            while (resultset!!.next()) {
                println(resultset.getString("Database"))
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close()
                } catch (sqlEx: SQLException) {
                }

                resultset = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }

                stmt = null
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }
        }
    }

    /**
     * This method makes a connection to MySQL Server
     */
    fun getConnection():Boolean {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
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