import '../models/user.dart';

class AppData {
  // Passenger users (username → User)
  static final Map<String, User> passengers = {};

  // Admin credentials (hardcoded)
  static const String adminUsername = 'admin';
  static const String adminPassword = 'admin123';

  // Seats (32 seats)
  static List<bool> seats = List.generate(32, (_) => false);

  // Seat owner (seatIndex → username)
  static Map<int, String> seatOwners = {};
}
