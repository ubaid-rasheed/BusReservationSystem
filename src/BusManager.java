import java.util.ArrayList;
import java.util.List;

public class BusManager {

    private final List<Bus> buses = new ArrayList<>();

    public void addBus(Bus bus) {
        buses.add(bus);
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public boolean isEmpty() {
        return buses.isEmpty();
    }
}
