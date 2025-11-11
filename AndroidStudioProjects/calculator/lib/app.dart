import 'package:flutter/material.dart';
import 'pages/calculator_page.dart';

class CalculatorApp extends StatelessWidget {
  const CalculatorApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp (
      title: '계산기',
      theme: ThemeData(
        //기본 색상을 바탕으로 색상 조합 랜덤 생성
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        useMaterial3: true,
      ),
      home: const CalculatorPage(),
    );
  }
}