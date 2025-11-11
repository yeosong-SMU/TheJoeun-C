import 'package:flutter/material.dart';

class ColorButtonPage extends StatefulWidget {
  const ColorButtonPage({super.key});

  @override
  State<ColorButtonPage> createState() => _ColorButtonPageState();
}

class _ColorButtonPageState extends State<ColorButtonPage> {
  Color boxColor = Colors.grey;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("버튼 색 바꾸기")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(width: 100, height: 100, color: boxColor),
            ElevatedButton(
                onPressed: () => setState(() => boxColor = Colors.red),
                child: const Text("빨강"),
            ),
            ElevatedButton(
              onPressed: () => setState(() => boxColor = Colors.blue),
              child: const Text("파랑"),
            ),
          ],
        ),
      ),
    );
  }
}