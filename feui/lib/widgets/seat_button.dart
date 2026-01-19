import 'package:flutter/material.dart';

class SeatButton extends StatelessWidget {
  final int seatNo;
  final bool isBooked;
  final VoidCallback onTap;

  const SeatButton({
    super.key,
    required this.seatNo,
    required this.isBooked,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: isBooked ? Colors.red : Colors.green,
        borderRadius: BorderRadius.circular(8),
      ),
      child: InkWell(
        onTap: onTap,
        child: Center(
          child: Text(
            seatNo.toString(),
            style: const TextStyle(
              color: Colors.white,
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ),
    );
  }
}
