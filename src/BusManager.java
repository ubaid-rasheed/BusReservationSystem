public class BusManager {

    Bus[] buses = new Bus[100];
    int count = 0;

    boolean add(Bus b) {
        if (find(b.id) != null) return false;
        buses[count++] = b;
        return true;
    }

    boolean remove(int id) {
        for (int i = 0; i < count; i++) {
            if (buses[i].id == id) {
                for (int j = i; j < count - 1; j++)
                    buses[j] = buses[j + 1];
                count--;
                return true;
            }
        }
        return false;
    }

    Bus find(int id) {
        for (int i = 0; i < count; i++)
            if (buses[i].id == id) return buses[i];
        return null;
    }
}
