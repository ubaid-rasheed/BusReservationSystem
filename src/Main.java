public class Main {
    public static void main(String[] args) throws Exception {
        ReservationService service = new ReservationService();
        SimpleHttpServer server = new SimpleHttpServer(service);
        server.start();
    }
}
