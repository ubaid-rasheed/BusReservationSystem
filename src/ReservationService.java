public class ReservationService {

    private UserManager userManager = new UserManager();

    // ---------------- PASSENGER AUTH ----------------
    public String passengerLogin(String data) {
        String[] parts = data.split("&");
        String username = parts[0].split("=")[1];
        String password = parts[1].split("=")[1];

        return userManager.loginOrSignup(username, password);
    }

    // ---------------- ADMIN AUTH ----------------
    public boolean adminLogin(String data) {
        String[] parts = data.split("&");
        String username = parts[0].split("=")[1];
        String password = parts[1].split("=")[1];

        return username.equals("admin") && password.equals("admin123");
    }

    // ---------------- BUS FEATURES ----------------
    public String getAllBuses() {
        return "[]"; // will replace with real buses
    }

    public String bookSeat(String data) {
        return "Seat booked";
    }

    public String cancelSeat(String data) {
        return "Seat cancelled";
    }

    public String adminCancelSeat(String data) {
        return "Admin cancelled seat";
    }
}
