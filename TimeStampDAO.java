import java.sql.*;

public class TimeStampDAO {

    public TimeStampDAO() {
    }

    public void addDB(int year, int day, int time, int youbi) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:stamptest.db");

            String tableName = "testTable3";
            String checkTable = "SELECT name FROM sqlite_master WHERE type='table' AND name ='testTable3'";
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(checkTable);

            if (!rs.next()) {
                String createTable = "CREATE TABLE testTable3 (year INTEGER, day INTEGER, time INTEGER,youbi INTEGER)";
                statement.executeUpdate(createTable);
            }

            String insertData = "INSERT INTO " + tableName + "(year,day,time,youbi) VALUES(" + year + "," + day + ","
                    + time + "," + youbi + ")";
            statement.executeUpdate(insertData);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}