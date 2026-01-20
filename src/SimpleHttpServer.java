import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

    private final ReservationService reservationService;

    public SimpleHttpServer(ReservationService service) {
        this.reservationService = service;
    }

    public void start() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // -------- GET ALL BUSES --------
        server.createContext("/buses", e -> {
            if (!e.getRequestMethod().equalsIgnoreCase("GET")) {
                send(e, "Method Not Allowed", 405);
                return;
            }

            String response = reservationService.getAllBuses();
            send(e, response, 200);
        });

        // -------- BOOK SEAT --------
        server.createContext("/book", e -> {
            if (!e.getRequestMethod().equalsIgnoreCase("POST")) {
                send(e, "Method Not Allowed", 405);
                return;
            }

            String body = read(e);
            String response = reservationService.bookSeat(body);
            send(e, response, 200);
        });

        // -------- CANCEL SEAT --------
        server.createContext("/cancel", e -> {
            if (!e.getRequestMethod().equalsIgnoreCase("POST")) {
                send(e, "Method Not Allowed", 405);
                return;
            }

            String body = read(e);
            String response = reservationService.cancelSeat(body);
            send(e, response, 200);
        });

        server.start();
        System.out.println("HTTP Server running on http://localhost:8080");
    }

    // -------- UTIL --------

    private String read(HttpExchange e) throws IOException {
        return new String(e.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private void send(HttpExchange e, String response, int code) throws IOException {
        e.sendResponseHeaders(code, response.getBytes().length);
        OutputStream os = e.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
