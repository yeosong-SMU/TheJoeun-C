import 'dart:math';

import 'package:flutter/material.dart';

class TapColorPage extends StatefulWidget {
  const TapColorPage({super.key});

  @override
  State<TapColorPage> createState() => _TapColorPageState();
}

class _TapColorPageState extends State<TapColorPage> {
  Color _bg = Colors.white;

  Color _randomColor() =>
      Color ((Random().nextDouble() * 0xFFFFFF).toInt()).withOpacity(1);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Tap 색상 바꾸기')),
      body: GestureDetector(
        onTap: () => setState(() => _bg = _randomColor()),
        child: AnimatedContainer(
          duration: const Duration(milliseconds: 250),
          color: _bg,
          child: const Center(
            child: Text('화면을 탭하세요', style: TextStyle(fontSize: 24)),
          ),
        ),
      ),
    );
  }
}