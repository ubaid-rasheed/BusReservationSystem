import java.io.*;
import java.util.Scanner;

 class BusReservationSystem {
    static Scanner sc = new Scanner(System.in);
    static BusManager busMgr = new BusManager();
    static DataHandler dataHandler = new DataHandler();

    public static void main(String[] args) {
        dataHandler.loadData();
        while (true) {
            System.out.println("\n=== BUS RESERVATION SYSTEM ===");
            System.out.println("1. Passenger\n2. Admin\n3. Exit");
            System.out.print("Choice: ");
            int ch = getInt();
            switch (ch) {
                case 1 -> passengerMenu();
                case 2 -> adminLogin();
                case 3 -> {
                    dataHandler.saveData();
                    System.out.println("Data Saved. Exiting.");
                    return;
                }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static void passengerMenu() {
        while (true) {
            System.out.println(
                    "\n-- PASSENGER --\n1. View Buses\n2. Book Seat\n3. Cancel Seat\n4. Availability\n5. Back");
            System.out.print("Choice: ");
            int ch = getInt();
            if (ch == 5)
                return;
            switch (ch) {
                case 1 -> busMgr.showBuses();
                case 2 -> {
                    busMgr.showBuses();
                    System.out.print("Enter Bus Serial Number: ");
                    int sn = getInt();
                    if (sn > 0 && sn <= busMgr.count) {
                        Bus b = busMgr.buses[sn - 1];
                        b.showSeats();
                        System.out.print("Enter Seat Number: ");
                        int s = getInt() - 1;
                        if (b.isAvail(s)) {
                            System.out.print("Enter Full Name of Passenger: ");
                            String n = getString();
                            System.out.print("Age: ");
                            int a = getInt();
                            System.out.print("Gender (M/F): ");
                            String gInput = getString();
                            char g = gInput.length() > 0 ? gInput.toUpperCase().charAt(0) : 'U';
                            b.book(s, n, a, g);
                            dataHandler.saveData();
                            System.out.println("Booking Confirmed!");
                        } else
                            System.out.println("Seat Unavailable or Invalid.");
                    } else
                        System.out.println("Invalid Bus Serial Number.");
                }
                case 3 -> {
                    System.out.print("Enter Bus Serial Number: ");
                    int sn = getInt();
                    if (sn > 0 && sn <= busMgr.count) {
                        Bus b = busMgr.buses[sn - 1];
                        System.out.print("Enter Seat Number to Cancel: ");
                        if (b.cancel(getInt() - 1)) {
                            dataHandler.saveData();
                            System.out.println("Cancellation Successful.");
                        } else
                            System.out.println("Cancellation Failed (Seat not booked or invalid).");
                    } else
                        System.out.println("Invalid Bus Serial Number.");
                }
                case 4 -> {
                    System.out.print("Enter Bus Serial Number: ");
                    int sn = getInt();
                    if (sn > 0 && sn <= busMgr.count) {
                        busMgr.buses[sn - 1].showSeats();
                    } else
                        System.out.println("Invalid Bus Serial Number.");
                }
            }
        }
    }

    static void adminLogin() {
        System.out.print("User: ");
        String u = getString();
        System.out.print("Pass: ");
        String p = getString();
        if (u.equals("DSU") && p.equals("DSA")) {
            while (true) {
                System.out
                        .println("\n-- ADMIN --\n1. Add Bus\n2. Remove Bus\n3. Show All\n4. Show Bookings\n5. Logout");
                System.out.print("Choice: ");
                int ch = getInt();
                if (ch == 5)
                    return;
                switch (ch) {
                    case 1 -> {
                        System.out.print("ID: ");
                        int id = getInt();
                        System.out.print("Name: ");
                        String n = getString();
                        System.out.print("Departure: ");
                        String s = getString();
                        System.out.print("Dest: ");
                        String d = getString();
                        System.out.print("Time: ");
                        String t = getString();
                        System.out.print("Seats: ");
                        int cap = getInt();
                        System.out.print("Fare: ");
                        double f = getDouble();
                        if (busMgr.add(new Bus(id, n, s, d, t, cap, f)))
                            System.out.println("Bus Added.");
                        else
                            System.out.println("Error: Bus Not Added (Duplicate ID or Full).");
                    }
                    case 2 -> {
                        System.out.print("ID: ");
                        if (busMgr.remove(getInt()))
                            System.out.println("Removed.");
                        else
                            System.out.println("Not found.");
                    }
                    case 3 -> busMgr.showBuses();
                    case 4 -> {
                        System.out.print("ID: ");
                        Bus b = busMgr.find(getInt());
                        if (b != null)
                            b.printManifest();
                        else
                            System.out.println("Bus not found.");
                    }
                }
            }
        } else
            System.out.println("Invalid login.");
    }


    static int getInt() {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                if (line.isEmpty())
                    continue;
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid Input. Enter a number: ");
            }
        }
    }

    static double getDouble() {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                if (line.isEmpty())
                    continue;
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid Input. Enter a valid amount: ");
            }
        }
    }

    static String getString() {
        return sc.nextLine().trim();
    }

    static class Bus {
        int id, cap;
        String name, departure, dest, time;
        double fare;
        boolean[] seats;
        String[] pNames;
        int[] pAges;
        char[] pGenders;
        int availSeats;

        Bus(int i, String n, String s, String d, String t, int c, double f) {
            id = i;
            name = n;
            departure = s;
            dest = d;
            time = t;
            cap = c;
            fare = f;
            seats = new boolean[c];
            pNames = new String[c];
            pAges = new int[c];
            pGenders = new char[c];
            availSeats = c; // Initially all seats are available
        }

        boolean isAvail(int s) {
            return s >= 0 && s < cap && !seats[s];
        }

        void book(int s, String n, int a, char g) {
            seats[s] = true;
            pNames[s] = n;
            pAges[s] = a;
            pGenders[s] = g;
            availSeats--;
        }

        boolean cancel(int s) {
            if (s < 0 || s >= cap || !seats[s])
                return false;
            seats[s] = false;
            pNames[s] = null;
            pAges[s] = 0;
            pGenders[s] = ' ';
            availSeats++;
            return true;
        }

        void showSeats() {
            for (int i = 0; i < cap; i++) {
                System.out.print((seats[i] ? "[X]" : "[" + (i + 1) + "]") + ((i + 1) % 4 == 0 ? "\n" : "\t"));
            }
            System.out.println();
        }

        void printManifest() {
            for (int i = 0; i < cap; i++)
                if (seats[i])
                    System.out.printf("Seat %d: %s (%d%c)\n", i + 1, pNames[i], pAges[i], pGenders[i]);
        }
    }

    static class BusManager {
        Bus[] buses = new Bus[100];
        int count = 0;

        boolean add(Bus b) {
            if (find(b.id) != null)
                return false;
            if (count < 100) {
                buses[count++] = b;
                return true;
            }
            return false;
        }

        boolean remove(int id) {
            for (int i = 0; i < count; i++)
                if (buses[i].id == id) {
                    for (int j = i; j < count - 1; j++)
                        buses[j] = buses[j + 1];
                    count--;
                    return true;
                }
            return false;
        }

        Bus find(int id) {
            for (int i = 0; i < count; i++)
                if (buses[i].id == id)
                    return buses[i];
            return null;
        }

        void showBuses() {
            System.out.println("--- Available Buses ---");
            if (count == 0) {
                System.out.println("There are no buses available at this time.");
            } else {
                for (int i = 0; i < count; i++)
                    System.out.printf("%d. [ID:%d] %s %s->%s Time: %s Rs: %.1f (%d/%d Seats)\n",
                            (i + 1), buses[i].id, buses[i].name, buses[i].departure, buses[i].dest, buses[i].time,
                            buses[i].fare, buses[i].availSeats, buses[i].cap);
            }
        }
    }

    static class DataHandler {
        void saveData() {
            try (PrintWriter pw = new PrintWriter(new FileWriter("buses_compact.txt"))) {
                for (int i = 0; i < busMgr.count; i++) {
                    Bus b = busMgr.buses[i];
                    pw.printf("%d,%s,%s,%s,%s,%d,%.2f\n", b.id, b.name, b.departure, b.dest, b.time, b.cap, b.fare);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter("bookings_compact.txt"))) {
                for (int i = 0; i < busMgr.count; i++) {
                    Bus b = busMgr.buses[i];
                    for (int s = 0; s < b.cap; s++)
                        if (b.seats[s])
                            pw.printf("%d,%d,%s,%d,%c\n", b.id, s, b.pNames[s], b.pAges[s], b.pGenders[s]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void loadData() {
            File f = new File("buses_compact.txt");
            if (f.exists())
                try (Scanner s = new Scanner(f)) {
                    while (s.hasNextLine()) {
                        String[] p = s.nextLine().split(",");
                        if (p.length >= 7) {
                            Bus b = new Bus(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4], Integer.parseInt(p[5]),
                                    Double.parseDouble(p[6]));
                            busMgr.add(b);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            File fb = new File("bookings_compact.txt");
            if (fb.exists())
                try (Scanner s = new Scanner(fb)) {
                    while (s.hasNextLine()) {
                        String[] p = s.nextLine().split(",");
                        if (p.length >= 5) {
                            Bus b = busMgr.find(Integer.parseInt(p[0]));
                            char g = p[4].length() > 0 ? p[4].charAt(0) : 'U';
                            if (b != null)
                                b.book(Integer.parseInt(p[1]), p[2], Integer.parseInt(p[3]), g);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}

