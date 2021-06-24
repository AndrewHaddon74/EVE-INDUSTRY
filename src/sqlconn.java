import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class sqlconn {

    public static Connection connect() {
        String url = "jdbc:sqlite:eve.db";

        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection connection = DriverManager.getConnection(url);
            return connection;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
