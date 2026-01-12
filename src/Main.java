import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static BusManager busMgr = new BusManager();
    static DataHandler dataHandler = new DataHandler(busMgr);
    static ReservationService service =
            new ReservationService(busMgr, dataHandler);

    public static void main(String[] args) {
        dataHandler.loadData();
        System.out.println("Bus Reservation System Loaded");

        // Later: console menu OR REST API OR Flutter
    }
}

