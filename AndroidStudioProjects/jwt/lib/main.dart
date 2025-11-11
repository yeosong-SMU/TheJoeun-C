import 'package:flutter/material.dart';

import 'api_client.dart';
import 'home_page.dart';
import 'login_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final api = ApiClient();
  bool _booting = true;
  bool _loggedIn = false;
  String? _username;

  @override
  void initState() {
    super.initState();
    _bootstrap();
  }

  Future<void> _bootstrap() async {
    final ok = await api.isAuthenticated();
    setState(() {
      _loggedIn = ok;
      _booting = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'JWT Login',
      debugShowCheckedModeBanner: false,
      home: _booting
        ? const Scaffold(body: Center(child: CircularProgressIndicator()))
          : (_loggedIn
            ? HomePage(
              api: api,
              //username: _username,
        username: 'test',
              onLoggedOut: _onLoggedOut,
            )
            : LoginPage(api: api, onLoggedIn: _onLoggedIn)),
    );
  }

  void _onLoggedIn(String name) {
    setState(() {
      _loggedIn = true;
      _username = name;
    });
  }

  void _onLoggedOut() {
    setState(() {
      _loggedIn = false;
      _username = null;
    });
  }
}