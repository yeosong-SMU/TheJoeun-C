import 'dart:math';
import 'package:flutter/material.dart';

class Vocab {
  final String word;
  final String meaning;

  const Vocab(this.word, this.meaning);
}

class Page1Basic extends StatefulWidget {
  const Page1Basic({super.key});

  @override
  State<Page1Basic> createState() => _Page1BasicState();
}

class _Page1BasicState extends State<Page1Basic> {
  final _rand = Random();
  final _words = const <Vocab>[
    Vocab('hello', '안녕'),
    Vocab('cat', '고양이'),
    Vocab('dog', '개'),
    Vocab('red', '빨간'),
    Vocab('water', '물'),
  ];

  int _index = 0;
  bool _showMeaning = false;

  void _next() {
    setState(() {
      _index = _rand.nextInt(_words.length);
      _showMeaning = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _next();
  }

  @override
  Widget build(BuildContext context) {
    final v = _words[_index];
    return Scaffold(
      appBar: AppBar(title: const Text('1. 기본 기능'), centerTitle: true),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(24),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(v.word,
                    style: const TextStyle(
                        fontSize: 40, fontWeight: FontWeight.bold)),
                const SizedBox(height: 12),
                AnimatedSwitcher(
                  duration: const Duration(milliseconds: 200),
                  child: _showMeaning
                      ? Text(v.meaning,
                          key: const ValueKey('m'),
                          style: const TextStyle(fontSize: 28))
                      : Text('뜻 보기',
                          key: const ValueKey('h'),
                          style: TextStyle(fontSize: 20, color: Colors.grey[600])),
                ),
                const SizedBox(height: 24),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    OutlinedButton(
                        onPressed: () =>
                          setState(() => _showMeaning = !_showMeaning),
                        child: Text(_showMeaning ? '뜻 숨기기' : '뜻 보기'),
                    ),
                    const SizedBox(width: 12),
                    FilledButton.icon(
                        onPressed: _next,
                        icon: const Icon(Icons.casino),
                        label: const Text('랜덤'),
                    ),
                  ],
                ),
              ],
            ),
        ),
      ),
    );
  }
}