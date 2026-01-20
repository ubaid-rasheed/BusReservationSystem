import 'package:flutter/material.dart';
import '../models/bus.dart';
import '../services/api_service.dart';

class SeatLayoutScreen extends StatefulWidget {
  final String username;

  const SeatLayoutScreen({super.key, required this.username});

  @override
  State<SeatLayoutScreen> createState() => _SeatLayoutScreenState();
}

class _SeatLayoutScreenState extends State<SeatLayoutScreen> {
  late Future<List<Bus>> busesFuture;

  final String username = 'demoUser'; // temp logged user

  @override
  void initState() {
    super.initState();
    _loadBuses();
  }

  void _loadBuses() {
    busesFuture = ApiService.fetchBuses();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Available Buses')),
      body: FutureBuilder<List<Bus>>(
        future: busesFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text(snapshot.error.toString()));
          }

          final buses = snapshot.data!;
          if (buses.isEmpty) {
            return const Center(child: Text('No buses available'));
          }

          return ListView.builder(
            itemCount: buses.length,
            itemBuilder: (context, index) {
              final bus = buses[index];
              final available = bus.seats.where((s) => !s).length;

              return Card(
                margin: const EdgeInsets.all(10),
                child: ListTile(
                  title: Text('${bus.departure} → ${bus.destination}'),
                  subtitle: Text(
                    '${bus.name} | ${bus.time}\nFare: Rs ${bus.fare}',
                  ),
                  trailing: Text('$available / ${bus.capacity}'),
                  onTap: () => _showSeats(bus),
                ),
              );
            },
          );
        },
      ),
    );
  }

  // ---------------- SHOW SEATS ----------------

  void _showSeats(Bus bus) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      builder: (_) => StatefulBuilder(
        builder: (context, setModalState) {
          return GridView.builder(
            padding: const EdgeInsets.all(16),
            itemCount: bus.capacity,
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 4,
              crossAxisSpacing: 10,
              mainAxisSpacing: 10,
            ),
            itemBuilder: (_, index) {
              final booked = bus.seats[index];

              return GestureDetector(
                onTap: booked ? null : () => _bookSeat(bus.id, index),
                child: Container(
                  decoration: BoxDecoration(
                    color: booked ? Colors.red : Colors.green,
                    borderRadius: BorderRadius.circular(6),
                  ),
                  child: Center(
                    child: Text(
                      'Seat ${index + 1}',
                      style: const TextStyle(color: Colors.white),
                    ),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }

  // ---------------- BOOK SEAT ----------------

  Future<void> _bookSeat(int busId, int seatIndex) async {
    final result = await ApiService.bookSeat(
      busId: busId,
      seatNumber: seatIndex + 1,
      username: username,
      age: 20,
      gender: 'M',
    );

    Navigator.pop(context); // close seat grid

    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(result)));
  }
}
