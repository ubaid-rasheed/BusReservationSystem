import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Bus> buses = new ArrayList<>();

        // -------- ADD SAMPLE BUSES --------

        buses.add(new Bus(
                1,
                "Express Coach",
                "City A",
                "City B",
                "10:00 AM",
                32,
                500
        ));

        buses.add(new Bus(
                2,
                "Luxury Line",
                "City A",
                "City C",
                "04:00 PM",
                40,
                700
        ));

        DataHandler handler = new DataHandler();
        ReservationService service = new ReservationService(buses, handler);

        try {
            SimpleHttpServer server = new SimpleHttpServer(service);
            server.start();
        } catch (IOException e) {
            System.out.println("Server failed to start");
        }
    }
}