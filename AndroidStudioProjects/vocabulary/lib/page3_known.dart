import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:shared_preferences/shared_preferences.dart';

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

class Page3Known extends StatefulWidget {
  const Page3Known({super.key});

  @override
  State<Page3Known> createState() => _Page3KnownState();
}

class _Page3KnownState extends State<Page3Known> {
  static const _key = 'known_words';
  late SharedPreferences _prefs;
  List<Vocab> _words = const [];
  Set<String> _known = {};
  bool _onlyKnown = false;
  bool _loading = true;

  Future<void> _init() async {
    _prefs = await SharedPreferences.getInstance();
    final list = await _loadWords();
    setState(() {
      _words = list;
      _known = {...?_prefs.getStringList(_key)};
      _loading = false;
    });
  }

  Future<void> _toggleKnown(String word) async {
    setState(() {
      _known.contains(word) ? _known.remove(word) : _known.add(word);
    });
    await _prefs.setStringList(_key, _known.toList());
  }

  @override
  void initState() {
    super.initState();
    _init();
  }

  @override
  Widget build(BuildContext context) {
    if (_loading)
      return const Scaffold(body: Center(child: CircularProgressIndicator()));
    final list = _onlyKnown
        ? _words.where((v) => _known.contains(v.word)).toList()
        : _words;

    return Scaffold(
      appBar: AppBar(
        title: Text('3. 학습 단어 저장 (${_known.length}개)'),
        actions: [
          Row(children: [
            const Text('완료 리스트'),
            Switch(
              value: _onlyKnown,
              onChanged: (v) => setState(() => _onlyKnown = v)
            ),
            const SizedBox(width: 8),
          ]),
        ],
      ),
      body: ListView.separated(
        itemCount: list.length,
        separatorBuilder: (_, __) => const Divider(height: 1),
        itemBuilder: (_, i) {
          final v = list[i];
          final isKnown = _known.contains(v.word);
          return ListTile(
            title: Text(v.word, style: const TextStyle(fontSize: 18)),
            subtitle: Text(v.meaning),
            trailing: IconButton(
                onPressed: () => _toggleKnown(v.word),
                icon: Icon(isKnown ? Icons.check_circle : Icons.circle_outlined,
                  color: isKnown ? Colors.teal : null),
                tooltip: isKnown ? '완료 해제' : '완료 표시',
            ),
          );
        },),
    );
  }
}