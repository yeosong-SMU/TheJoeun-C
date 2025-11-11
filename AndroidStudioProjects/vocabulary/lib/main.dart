import 'package:flutter/material.dart';
import 'page1_basic.dart';
import 'page2_json.dart';
import 'page3_known.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '영어 단어장',
      theme: ThemeData(useMaterial3: true, colorSchemeSeed: Colors.indigo),
      home: const HomeMenu(),
    );
  }
}

class HomeMenu extends StatelessWidget {
  const HomeMenu({super.key});

  @override
  Widget build(BuildContext context) {
    final items = [
      ('1. 기본 기능', const Page1Basic()),
      ('2. JSON', const Page2Json()),
      ('3. 단어 저장', const Page3Known()),
    ];

    return Scaffold(
      appBar: AppBar(title: const Text('영어 단어장 - 메뉴'), centerTitle: true),
      body: ListView.separated(
          separatorBuilder: (_, __) => const Divider(height: 1),
          itemCount: items.length,
          itemBuilder: (context, i) {
            final (title, page) = items[i];
            return ListTile(
              title: Text(title),
              trailing: const Icon(Icons.chevron_right),
              onTap: () => Navigator.push(
                context, MaterialPageRoute(builder: (_) => page),
              ),
            );
          },
      ),
    );
  }
}