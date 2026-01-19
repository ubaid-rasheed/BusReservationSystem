import java.util.HashMap;
import java.util.Map;

public class Bus {

    private final int id;
    private final String name, from, to, time;
    private final int totalSeats;
    private final int price;

    private final Map<Integer, String> bookedSeats = new HashMap<>();

    public Bus(int id, String name, String from, String to,
               String time, int totalSeats, int price) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.time = time;
        this.totalSeats = totalSeats;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    // BOOK SEAT
    public boolean bookSeat(int seat, String username) {
        if (seat < 1 || seat > totalSeats) return false;
        if (bookedSeats.containsKey(seat)) return false;
        bookedSeats.put(seat, username);
        return true;
    }

    // CANCEL SEAT (Admin)
    public void cancelSeat(int seat) {
        bookedSeats.remove(seat);
    }

    // JSON for Flutter
    public String toJson() {
        return "{"
                + "\"id\":" + id + ","
                + "\"name\":\"" + name + "\","
                + "\"from\":\"" + from + "\","
                + "\"to\":\"" + to + "\","
                + "\"time\":\"" + time + "\","
                + "\"price\":" + price + ","
                + "\"totalSeats\":" + totalSeats + ","
                + "\"bookedSeats\":" + bookedSeats.keySet()
                + "}";
    }
}
