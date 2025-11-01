import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    // Variables to hold config values
    private static String URL;
    private static String USER;
    private static String PASS;

    // This block runs automatically once when the class loads
    static {
        Properties props = new Properties();
        try {
            // Load config file (should be in your project root)
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            // Assign values from the file
            URL = props.getProperty("DB_URL");
            USER = props.getProperty("DB_USER");
            PASS = props.getProperty("DB_PASS");

            fis.close();
            System.out.println("✅ Config loaded successfully!");
        } catch (IOException e) {
            System.out.println("⚠️ Failed to load config.properties file!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected to database as: " + USER);
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        return con;
    }
}
