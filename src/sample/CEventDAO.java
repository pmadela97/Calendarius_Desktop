package sample;

import java.sql.*;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.SQLException;



public interface CEventDAO {

    public default Connection getConnection(String user, String password) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ceventlist"+
                "?verifyServerCertificate=false"+
                "&useSSL=true"+
                "&requireSSL=true", user, password);
        return connection;
    }
    public default void insert(CEvent event) throws  SQLException{
       Connection con = getConnection("admin","admin");
        String sql = "INSERT INTO CEVENT(NAME,STARTDATETIME,ENDDATETIME,LOCALIZATION,DESCRIPTION) VALUES "
                + "(" + "\"" + event.getName() + "\",\"" + event.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "\",\"" + event.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "\",\"" + event.getLocalization() + "\",\"" + event.getDescription() + "\")";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.execute();
        con.close();
    }

    public default void update(CEvent event) throws SQLException {
        Connection connection = getConnection("admin","admin");
        String sql = "UPDATE CEVENT SET "
                + "NAME = " + "\"" + event.getName() + "\"" + ','
                + "STARTDATETIME=" + "\"" + event.getStartDateTime() + "\"" + ','
                + "ENDDATETIME=" + "\"" + event.getEndDateTime() + "\"" + ','
                + "LOCALIZATION=" + "\"" + event.getLocalization() + "\"" + ','
                + "DESCRIPTION=" + "\"" + event.getDescription() + "\""
                + " WHERE ID=" + event.getId();
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        connection.close();
    }



    public default boolean getListFromDataBase(List<CEvent> list) throws  SQLException{
        Connection con = getConnection("admin","admin");
        String sql = "select * from CEVENT;";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        list.clear();
        while(resultSet.next()){
            CEvent event = new CEvent(resultSet.getString("name"), resultSet.getTimestamp("startdatetime").toLocalDateTime());
            event.setId(resultSet.getInt("id"));
            event.setEndDateTime(resultSet.getTimestamp("enddatetime")!=null ? resultSet.getTimestamp("enddatetime").toLocalDateTime() : null );
            event.setLocalization(resultSet.getString("localization"));
            event.setDescription(resultSet.getString("description"));
            System.out.println(event);
            list.add(event);
    }
        con.close();
        return true;
    }

    public default boolean sendListToDataBase(List<CEvent> list) throws  SQLException{
        Connection con =getConnection("admin","admin");
        String sql = "DELETE FROM CEVENT;";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.execute();
        for(CEvent event : list){
            sql = "INSERT INTO CEVENT(NAME,STARTDATETIME,ENDDATETIME,LOCALIZATION,DESCRIPTION) VALUES "
                    +"("+"\""+event.getName()+"\",\""+event.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    +"\",\""+event.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    +"\",\""+event.getLocalization()+"\",\""+event.getDescription()+"\")";
            System.out.println(sql);

                PreparedStatement statement1 = con.prepareStatement(sql);
                statement1.execute();
        }
        con.close();
        return true;

        }
}
