import 'dart:math';

import 'package:flutter/material.dart';

class QuotePage extends StatefulWidget {
  const QuotePage({super.key});

  @override
  State<QuotePage> createState() => _QuotePageState();
}

class _QuotePageState extends State<QuotePage> {
  final quotes = const [
    "행동은 모든 성공의 기초다.",
    "오늘의 노력은 내일의 자산이다.",
    "포기하지 않는 한 실패는 없다.",
    "작은 습관이 큰 변화를 만든다.",
    "배움은 끝이 없는 여행이다.",
    "실패는 성공으로 가는 디딤돌이다.",
  ];
  String current = "버튼을 눌러 명언을 확인하세요!";

  void nextQuote() {
    setState(() => current = quotes[Random().nextInt(quotes.length)]);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('랜덤 명언 표시기')),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(24),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                current,
                textAlign: TextAlign.center,
                style: const TextStyle(fontSize: 24),
              ),
              const SizedBox(height: 24,),
              FilledButton.icon(
                onPressed: nextQuote,
                icon: const Icon(Icons.format_quote),
                label: const Text('다음 명언'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}