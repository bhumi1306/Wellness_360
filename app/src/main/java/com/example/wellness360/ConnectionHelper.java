package com.example.wellness360;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String uname, passwd,ip,port,database;
    @SuppressLint("NewApi")
    public Connection connectionclass() {
        ip = "192.168.1.37";
        database= "wellnessdb";
        uname= "bh";
        passwd= "";
        port= "3306";
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection =null;
        String ConnectionURL = null;

        try
        {
           Class.forName("net.sourceforge.jtds.jdbc.Driver");
           ConnectionURL = "jdbc:jtds:sqlserver://" +ip + ":" + port +";databasename" + database + ";user" +uname +";password" +passwd + ";";
           connection= DriverManager.getConnection(ConnectionURL);
        }
        catch (Exception ex){
            Log.e("Error" , ex.getMessage());
        }


        return connection;

    }
}
