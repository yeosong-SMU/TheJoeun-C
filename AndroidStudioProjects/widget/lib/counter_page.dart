import 'package:flutter/material.dart';

class CounterPage extends StatefulWidget {
  const CounterPage({super.key});

  @override
  State <CounterPage> createState() => _CounterPageState();
}

class _CounterPageState extends State<CounterPage> {
  int count = 0;

  void increase() {
    setState(() {
      count++;
    });
  }

  void decrease() {
    setState(() {
      count--;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("카운터")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("값: $count", style: const TextStyle(fontSize: 24)),
            ElevatedButton(
                onPressed: increase,
                child: const Text("증가")),
            ElevatedButton(
                onPressed: decrease,
                child: const Text("감소")),
          ],
        ),
      ),
    );
  }
}