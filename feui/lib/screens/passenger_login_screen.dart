import 'package:flutter/material.dart';
import '../models/user.dart';
import 'seat_layout_screen.dart';
import 'role_select_screen.dart';

class PassengerLoginScreen extends StatefulWidget {
  const PassengerLoginScreen({super.key});

  @override
  State<PassengerLoginScreen> createState() => _PassengerLoginScreenState();
}

class _PassengerLoginScreenState extends State<PassengerLoginScreen> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  // TEMP IN-MEMORY USERS (later → Java backend)
  static final Map<String, User> _users = {};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Passenger Login'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pushReplacement(
              context,
              MaterialPageRoute(builder: (_) => const RoleSelectScreen()),
            );
          },
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: usernameController,
              decoration: const InputDecoration(
                labelText: 'Username',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 15),
            TextField(
              controller: passwordController,
              obscureText: true,
              decoration: const InputDecoration(
                labelText: 'Password',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 25),
            SizedBox(
              width: double.infinity,
              height: 50,
              child: ElevatedButton(
                onPressed: _loginOrAutoSignup,
                child: const Text('Continue'),
              ),
            ),
            const SizedBox(height: 12),
            const Text(
              'Existing user → Login\nNew user → Auto Signup',
              textAlign: TextAlign.center,
              style: TextStyle(color: Colors.grey),
            ),
          ],
        ),
      ),
    );
  }

  // ---------------- LOGIC ----------------

  void _loginOrAutoSignup() {
    final username = usernameController.text.trim();
    final password = passwordController.text.trim();

    if (username.isEmpty || password.isEmpty) {
      _showError('Username and password required');
      return;
    }

    if (_users.containsKey(username)) {
      // LOGIN
      if (_users[username]!.password != password) {
        _showError('Wrong password');
        return;
      }
    } else {
      // AUTO SIGNUP
      _users[username] = User(username: username, password: password);
    }

    // SUCCESS → SEAT LAYOUT
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (_) => SeatLayoutScreen(username: username)),
    );
  }

  void _showError(String msg) {
    showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text('Error'),
        content: Text(msg),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('OK'),
          ),
        ],
      ),
    );
  }
}
