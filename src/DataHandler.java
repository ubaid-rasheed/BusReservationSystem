import java.io.*;
import java.util.Scanner;

public class DataHandler {

    BusManager busMgr;

    DataHandler(BusManager bm) {
        busMgr = bm;
    }

    void saveData() {

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter("buses_compact.txt"))) {
            for (int i = 0; i < busMgr.count; i++) {
                Bus b = busMgr.buses[i];
                pw.printf("%d,%s,%s,%s,%s,%d,%.2f\n",
                        b.id, b.name, b.departure, b.dest,
                        b.time, b.cap, b.fare);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter("bookings_compact.txt"))) {
            for (int i = 0; i < busMgr.count; i++) {
                Bus b = busMgr.buses[i];
                for (int s = 0; s < b.cap; s++) {
                    if (b.seats[s]) {
                        pw.printf("%d,%d,%s,%d,%c\n",
                                b.id, s, b.pNames[s],
                                b.pAges[s], b.pGenders[s]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadData() {
        File f = new File("buses_compact.txt");
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                Bus b = new Bus(
                        Integer.parseInt(p[0]),
                        p[1], p[2], p[3], p[4],
                        Integer.parseInt(p[5]),
                        Double.parseDouble(p[6])
                );
                busMgr.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
