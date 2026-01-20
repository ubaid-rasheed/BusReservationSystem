
public class Bus {

    private int id;
    private String name;
    private String departure;
    private String destination;
    private String time;
    private int capacity;
    private int fare;

    private boolean[] booked;
    private String[] bookedBy;

    public Bus(int id, String name, String departure, String destination,
               String time, int capacity, int fare) {

        this.id = id;
        this.name = name;
        this.departure = departure;
        this.destination = destination;
        this.time = time;
        this.capacity = capacity;
        this.fare = fare;

        booked = new boolean[capacity];
        bookedBy = new String[capacity];
    }

    // --------- GETTERS ---------

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public String getTime() { return time; }
    public int getCapacity() { return capacity; }
    public int getFare() { return fare; }

    // --------- SEAT LOGIC ---------

    public boolean isBooked(int seat) {
        return booked[seat];
    }

    public String getBookedBy(int seat) {
        return bookedBy[seat];
    }

    public boolean bookSeat(int seat, String user) {
        if (booked[seat]) return false;

        booked[seat] = true;
        bookedBy[seat] = user;
        return true;
    }

    public void cancelSeat(int seat) {
        booked[seat] = false;
        bookedBy[seat] = null;
    }

    // --------- JSON FOR FLUTTER ---------

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\":").append(id).append(",");
        sb.append("\"name\":\"").append(name).append("\",");
        sb.append("\"departure\":\"").append(departure).append("\",");
        sb.append("\"destination\":\"").append(destination).append("\",");
        sb.append("\"time\":\"").append(time).append("\",");
        sb.append("\"capacity\":").append(capacity).append(",");
        sb.append("\"fare\":").append(fare).append(",");
        sb.append("\"seats\":[");

        for (int i = 0; i < capacity; i++) {
            sb.append(booked[i]);
            if (i < capacity - 1) sb.append(",");
        }

        sb.append("]}");
        return sb.toString();
    }
}