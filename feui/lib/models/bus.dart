class Bus {
  final int id;
  final String name;
  final String departure;
  final String destination;
  final String time;
  final int capacity;
  final int fare;
  final List<bool> seats;

  Bus({
    required this.id,
    required this.name,
    required this.departure,
    required this.destination,
    required this.time,
    required this.capacity,
    required this.fare,
    required this.seats,
  });

  factory Bus.fromJson(Map<String, dynamic> json) {
    return Bus(
      id: (json['id'] as num).toInt(),
      name: json['name'],
      departure: json['departure'],
      destination: json['destination'],
      time: json['time'],
      capacity: (json['capacity'] as num).toInt(),
      fare: (json['fare'] as num).toInt(),
      seats: List<bool>.from(json['seats']),
    );
  }
}
