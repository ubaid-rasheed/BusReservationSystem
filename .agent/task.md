# Task: Fix Seat Selection and Persistence in Bus Reservation System

## Status
- [x] Analyze the codebase to understand the issue.
- [x] Fix file path in `DataHandler.java` to point to `src/data/bookings.txt`.
- [x] Fix data format mismatch in `ApiService.dart` (change JSON to CSV-style string).
- [x] Verify compilation of Java backend.

## Details
The main issues were:
1.  **Format Mismatch**: The Flutter app was sending a JSON body for booking/cancellation, but the Java backend (`ReservationService`) expected a comma-separated string (`busId,seat,user`). This caused the backend to crash or return an error during parsing, preventing seat selection.
2.  **Persistence Path**: The `DataHandler` was trying to save to `bookings.txt` in the current directory, but the intended file was in `src/data/bookings.txt`.

## Fixes
- Modified `feui/lib/services/api_service.dart` to send `busId,seatNumber,username` string.
- Modified `src/DataHandler.java` to use `src/data/bookings.txt`.
