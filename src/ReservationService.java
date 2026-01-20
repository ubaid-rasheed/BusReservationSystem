import java.util.List;

public class ReservationService {

    private List<Bus> buses;
    private DataHandler dataHandler;

    public ReservationService(List<Bus> buses, DataHandler handler) {
        this.buses = buses;
        this.dataHandler = handler;

        // load previous bookings
        dataHandler.loadBookings(buses);
    }

    // -------- GET ALL BUSES --------

    public String getAllBuses() {
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < buses.size(); i++) {
            json.append(buses.get(i).toJson());
            if (i < buses.size() - 1) json.append(",");
        }

        json.append("]");
        return json.toString();
    }

    // -------- BOOK SEAT --------
    // Format: busId,seat,user

    public String bookSeat(String body) {
        try {
            String[] p = body.split(",");

            int busId = Integer.parseInt(p[0]);
            int seat = Integer.parseInt(p[1]) - 1;
            String user = p[2];

            for (Bus bus : buses) {
                if (bus.getId() == busId) {

                    boolean ok = bus.bookSeat(seat, user);
                    if (!ok) return "SEAT_ALREADY_BOOKED";

                    dataHandler.saveBookings(buses);
                    return "BOOKED_SUCCESSFULLY";
                }
            }

            return "BUS_NOT_FOUND";

        } catch (Exception e) {
            return "ERROR";
        }
    }

    // -------- CANCEL SEAT --------
    // Format: busId,seat

    public String cancelSeat(String body) {
        try {
            String[] p = body.split(",");

            int busId = Integer.parseInt(p[0]);
            int seat = Integer.parseInt(p[1]) - 1;

            for (Bus bus : buses) {
                if (bus.getId() == busId) {
                    bus.cancelSeat(seat);
                    dataHandler.saveBookings(buses);
                    return "CANCELLED";
                }
            }

            return "BUS_NOT_FOUND";

        } catch (Exception e) {
            return "ERROR";
        }
    }
}