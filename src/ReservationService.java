public class ReservationService {

    BusManager busMgr;
    DataHandler dataHandler;

    ReservationService(BusManager bm, DataHandler dh) {
        busMgr = bm;
        dataHandler = dh;
    }

    boolean bookSeat(int busId, int seat, String name, int age, char gender) {
        Bus b = busMgr.find(busId);
        if (b != null && b.isAvail(seat)) {
            b.book(seat, name, age, gender);
            dataHandler.saveData();
            return true;
        }
        return false;
    }

    boolean cancelSeat(int busId, int seat) {
        Bus b = busMgr.find(busId);
        if (b != null && b.cancel(seat)) {
            dataHandler.saveData();
            return true;
        }
        return false;
    }
}
