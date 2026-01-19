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

  // TEMP logged-in user
  final String username = 'demoUser';

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
              final availableSeats = bus.seats.where((s) => !s).length;

              return Card(
                margin: const EdgeInsets.all(10),
                child: ListTile(
                  title: Text('${bus.departure} → ${bus.destination}'),
                  subtitle: Text(
                    '${bus.name} | ${bus.time}\nFare: Rs ${bus.fare}',
                  ),
                  trailing: Text(
                    '$availableSeats/${bus.capacity}',
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                  onTap: () => _showSeats(bus),
                ),
              );
            },
          );
        },
      ),
    );
  }

  // ================= SEAT GRID =================

  void _showSeats(Bus bus) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      builder: (_) => GridView.builder(
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
            // Tap → Book seat
            onTap: booked ? null : () => _bookSeatDialog(bus, index),

            // Long press → Cancel seat
            onLongPress: booked ? () => _confirmCancel(bus, index) : null,

            child: Container(
              decoration: BoxDecoration(
                color: booked ? Colors.red : Colors.green,
                borderRadius: BorderRadius.circular(6),
              ),
              child: Center(
                child: Text(
                  'Seat ${index + 1}',
                  style: const TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }

  // ================= BOOK SEAT =================

  void _bookSeatDialog(Bus bus, int seatIndex) {
    final ageController = TextEditingController();
    String gender = 'M';

    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text('Book Seat ${seatIndex + 1}'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextField(
              controller: ageController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Age'),
            ),
            const SizedBox(height: 10),
            DropdownButton<String>(
              value: gender,
              items: const [
                DropdownMenuItem(value: 'M', child: Text('Male')),
                DropdownMenuItem(value: 'F', child: Text('Female')),
              ],
              onChanged: (v) => gender = v!,
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancel'),
          ),
          ElevatedButton(
            onPressed: () async {
              final result = await ApiService.bookSeat(
                busId: bus.id,
                seatNumber: seatIndex + 1,
                username: username,
                age: int.tryParse(ageController.text) ?? 0,
                gender: gender,
              );

              Navigator.pop(context);

              ScaffoldMessenger.of(
                context,
              ).showSnackBar(SnackBar(content: Text(result)));

              setState(_loadBuses);
            },
            child: const Text('Confirm'),
          ),
        ],
      ),
    );
  }

  // ================= CANCEL SEAT =================

  void _confirmCancel(Bus bus, int seatIndex) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text('Cancel Booking'),
        content: Text('Cancel Seat ${seatIndex + 1}?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('No'),
          ),
          ElevatedButton(
            onPressed: () async {
              final result = await ApiService.cancelSeat(
                busId: bus.id,
                seatNumber: seatIndex + 1,
              );

              Navigator.pop(context);

              ScaffoldMessenger.of(
                context,
              ).showSnackBar(SnackBar(content: Text(result)));

              setState(_loadBuses);
            },
            child: const Text('Yes, Cancel'),
          ),
        ],
      ),
    );
  }
}
