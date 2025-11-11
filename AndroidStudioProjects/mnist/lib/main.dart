import 'package:flutter/material.dart';

import 'draw_page.dart';

void main() {
  runApp(const MnistApp());
}

class MnistApp extends StatelessWidget {
  const MnistApp({super.key});

  @override
  Widget build(BuildContext context) => MaterialApp(
    title: 'MNIST 숫자 인식',
    theme: ThemeData(
      colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
      useMaterial3: true,
    ),
    home: const DrawPage(),
  );
}