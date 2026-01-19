import java.util.*;

public class BusManager {

    private final List<Bus> buses = new ArrayList<>();

    public void add(Bus bus) {
        buses.add(bus);
    }

    public boolean isEmpty() {
        return buses.isEmpty();
    }

    public Bus find(int busId) {
        for (Bus b : buses) {
            if (b.getId() == busId) return b;
        }
        return null;
    }

    // Used by Flutter (JSON response)
    public String toJson() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < buses.size(); i++) {
            sb.append(buses.get(i).toJson());
            if (i < buses.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public List<Bus> getAll() {
        return buses;
    }
}
