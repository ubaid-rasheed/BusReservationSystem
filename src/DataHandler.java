import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class DataHandler {

    private final String FILE = "src/data/bookings.txt";

    // -------- SAVE BOOKINGS --------

    public void saveBookings(List<Bus> buses) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {

            for (Bus b : buses) {
                for (int i = 0; i < b.getCapacity(); i++) {
                    if (b.isBooked(i)) {
                        pw.println(b.getId() + "," + i + "," + b.getBookedBy(i));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }

    // -------- LOAD BOOKINGS --------

    public void loadBookings(List<Bus> buses) {
        File f = new File(FILE);
        if (!f.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                int busId = Integer.parseInt(p[0]);
                int seat = Integer.parseInt(p[1]);
                String user = p[2];

                for (Bus b : buses) {
                    if (b.getId() == busId) {
                        b.bookSeat(seat, user);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Load failed");
        }
    }
}