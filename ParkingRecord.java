public class ParkingRecord {
    int recordId;
    Vehicle vehicle;
    ParkingSlot slot;
    long entryTime, exitTime;
    double fee;

    ParkingRecord(int id, Vehicle v, ParkingSlot s) {
        recordId = id;
        vehicle = v;
        slot = s;
        entryTime = System.currentTimeMillis();
    }

    void closeRecord() {
        exitTime = System.currentTimeMillis();
        long duration = (exitTime - entryTime) / 1000; // seconds
        fee = duration * 0.5; // Rs. 0.5 per sec
    }
}
