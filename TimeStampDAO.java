import java.io.File;
import java.io.IOException;
import java.sql.*;

public class TimeStampDAO {

    public TimeStampDAO() {
    }

    public void addDB(int year, int day, int time, int youbi) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");

            String dbFilePath = "./timeStamp.db";
            File dbFile = new File(dbFilePath);

            if (!dbFile.exists()) {
                dbFile.createNewFile();
            }

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);

            String tableName = "Stamps";
            String checkTable = "SELECT name FROM sqlite_master WHERE type='table' AND name ='Stamps'";
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(checkTable);

            if (!rs.next()) {
                String createTable = "CREATE TABLE Stamps (year INTEGER, day INTEGER, time INTEGER,youbi INTEGER)";
                statement.executeUpdate(createTable);
            }

            String insertData = "INSERT INTO " + tableName + "(year,day,time,youbi) VALUES(" + year + "," + day + ","
                    + time + "," + youbi + ")";
            statement.executeUpdate(insertData);

        } catch (IOException e) {
            e.printStackTrace();
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