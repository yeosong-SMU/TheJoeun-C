import 'dart:convert';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart' show rootBundle;

class Vocab {
  final String word;
  final String meaning;

  const Vocab(this.word, this.meaning);

  factory Vocab.fromMap(Map<String, dynamic> m) =>
      Vocab(m['word'], m['meaning']);
}

Future<List<Vocab>> _loadWords() async {
  final s = await rootBundle.loadString('assets/words.json');
  final data = (jsonDecode(s) as List).cast<Map<String, dynamic>>();
  return data.map(Vocab.fromMap).toList();
}

class Page2Json extends StatefulWidget {
  const Page2Json({super.key});

  @override
  State<Page2Json> createState() => _Page2JsonState();
}

class _Page2JsonState extends State<Page2Json> {
  final _rand = Random();
  List<Vocab> _words = const [];
  int _index = 0;
  bool _showMeaning = false;
  bool _loading = true;

  Future<void> _load() async {
    final list = await _loadWords();
    setState(() {
      _words = list;
      _index = _rand.nextInt(_words.length);
      _showMeaning = false;
      _loading = false;
    });
  }

  void _next() {
    setState(() {
      _index = _rand.nextInt(_words.length);
      _showMeaning = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _load();
  }

  @override
  Widget build(BuildContext context) {
    if(_loading)
      return const Scaffold(body: Center(child: CircularProgressIndicator()));
    final v = _words[_index];
    return Scaffold(
      appBar: AppBar(title: const Text('2. JSON 로드'), centerTitle: true),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(24),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [Text(v.word, style: const TextStyle(
              fontSize: 40, fontWeight: FontWeight.bold
            )),
            const SizedBox(height: 12),
            AnimatedSwitcher(
                duration: const Duration(milliseconds: 200),
                child: _showMeaning
                    ? Text(v.meaning, key: const ValueKey('m'), style: const TextStyle(fontSize: 28))
                    : Text('뜻 보기', key: const ValueKey('h'), style: TextStyle(fontSize: 20, color: Colors.grey[600])),
            ),
              const SizedBox(height: 24),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  OutlinedButton(
                      onPressed: () => setState(() => _showMeaning = !_showMeaning),
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