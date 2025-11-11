import 'package:flutter/material.dart';

import 'home_page.dart';
import 'signup_page.dart';

void main() {
  runApp(const MemberApp());
}

class MemberApp extends StatelessWidget {
  const MemberApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: '회원 관리',
      initialRoute: '/',
      routes: {
        '/' : (_) => const HomePage(),
        '/signup': (_) => const SignUpPage(),
      },
    );
  }
}