public class ParkingSlot {
    int slotNumber;
    boolean occupied;
    Vehicle vehicle;

    ParkingSlot(int n) { 
        slotNumber = n; 
        occupied = false; 
    }

    boolean checkAvailability() { 
        return !occupied; 
    }

    void assignVehicle(Vehicle v) { 
        occupied = true; 
        vehicle = v; 
    }

    void freeSlot() {
        occupied = false; 
        vehicle = null; 
    }

    String getStatus() {
        return "Slot " + slotNumber + (occupied ? " - Occupied by " + vehicle.vehicleNumber : " - Available");
    }
}
