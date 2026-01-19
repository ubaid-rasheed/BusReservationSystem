class Bus {
  final int id;
  final String name;
  final String departure;
  final String destination;
  final String time;
  final int fare;
  final int capacity;
  final List<bool> seats;

  Bus({
    required this.id,
    required this.name,
    required this.departure,
    required this.destination,
    required this.time,
    required this.fare,
    required this.capacity,
    required this.seats,
  });

  factory Bus.fromJson(Map<String, dynamic> json) {
    return Bus(
      id: json['id'],
      name: json['name'],
      departure: json['from'],
      destination: json['to'],
      time: json['time'],
      fare: json['fare'],
      capacity: json['capacity'],
      seats: List<bool>.from(json['seats']),
    );
  }
}
