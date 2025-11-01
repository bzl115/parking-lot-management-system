public class ParkingManager {
    String managerId, password;

    ParkingManager(String id, String pass) {
        this.managerId = id;
        this.password = pass;
    }

    boolean login(String id, String pass) { 
        return id.equals(managerId) && pass.equals(password); 
    }

    void assignSlotToVehicle(ParkingSlot slot, Vehicle v) { 
        slot.assignVehicle(v); 
    }
}
