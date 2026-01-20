import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/bus.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8080';

  // GET buses
  static Future<List<Bus>> fetchBuses() async {
    final response = await http.get(Uri.parse('$baseUrl/buses'));

    if (response.statusCode != 200) {
      throw Exception('Failed to load buses');
    }

    final List data = jsonDecode(response.body);
    return data.map((e) => Bus.fromJson(e)).toList();
  }

  // BOOK seat
  static Future<String> bookSeat({
    required int busId,
    required int seatNumber,
    required String username,
    required int age,
    required String gender,
  }) async {
    final response = await http.post(
      Uri.parse('$baseUrl/book'),
      body: '$busId,$seatNumber,$username',
    );

    return response.body;
  }

  // CANCEL seat
  static Future<String> cancelSeat({
    required int busId,
    required int seatNumber,
  }) async {
    final response = await http.post(
      Uri.parse('$baseUrl/cancel'),
      body: '$busId,$seatNumber',
    );

    return response.body;
  }
}
