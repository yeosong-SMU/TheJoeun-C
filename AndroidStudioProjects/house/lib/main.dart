import 'package:flutter/material.dart';
import 'predict_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '주택 가격 예측',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const PricePredictPage(),
    );
  }
}