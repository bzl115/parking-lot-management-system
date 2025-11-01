import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Admin extends ParkingManager {
    private static String storedId;
    private static String storedPass;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
            storedId = props.getProperty("ADMIN_USER");
            storedPass = props.getProperty("ADMIN_PASS");
        } catch (IOException e) {
            e.printStackTrace();
            // Default fallback (optional)
            storedId = "admin";
            storedPass = "admin123";
        }
    }

    Admin(String id, String pass) { 
        super(id, pass); 
    }

    @Override
    boolean login(String id, String pass) { 
        return id.equals(storedId) && pass.equals(storedPass); 
    }

    ParkingManager register(String id, String pass) { 
        return new ParkingManager(id, pass); 
    }
}
