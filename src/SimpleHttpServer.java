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

        server.createContext("/login/passenger", e -> {
    if (!e.getRequestMethod().equals("POST")) {
        send(e, "Method Not Allowed", 405);
        return;
    }
    String data = read(e);
    send(e, reservationService.passengerLogin(data), 200);
});

server.createContext("/login/admin", e -> {
    if (!e.getRequestMethod().equals("POST")) {
        send(e, "Method Not Allowed", 405);
        return;
    }
    String data = read(e);
    boolean ok = reservationService.adminLogin(data);
    send(e, ok ? "OK" : "FAIL", 200);
});

server.createContext("/admin/cancel", e -> {
    String data = read(e);
    send(e, reservationService.adminCancelSeat(data), 200);
});

        // GET all buses + seat status
        server.createContext("/buses", exchange -> {
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                send(exchange, "Method Not Allowed", 405);
                return;
            }
            send(exchange, reservationService.getAllBuses(), 200);
        });

        // POST book seat
        server.createContext("/book", exchange -> {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                send(exchange, "Method Not Allowed", 405);
                return;
            }
            String body = read(exchange);
            send(exchange, reservationService.bookSeat(body), 200);
        });

        // POST cancel seat
        server.createContext("/cancel", exchange -> {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                send(exchange, "Method Not Allowed", 405);
                return;
            }
            String body = read(exchange);
            send(exchange, reservationService.cancelSeat(body), 200);
        });

        server.start();
        System.out.println("HTTP Server running on http://localhost:8080");
    }

    private String read(HttpExchange ex) throws IOException {
        return new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private void send(HttpExchange ex, String response, int code) throws IOException {
        ex.sendResponseHeaders(code, response.getBytes().length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
