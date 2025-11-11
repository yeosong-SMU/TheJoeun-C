import 'package:flutter/material.dart';

import 'tabcolor_page.dart';
import 'fontslider_page.dart';
import 'quote_page.dart';


void main() {
  runApp(const DemoApp());
}

class DemoApp extends StatelessWidget {
  const DemoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '응용예제',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home: const MenuPage(),
    );
  }
}

class MenuPage extends StatelessWidget {
  const MenuPage({super.key});

  @override
  Widget build(BuildContext context) {
    final items = <(String, Widget)>[
      ('색상 바꾸기', const TapColorPage()),
      ('슬라이더로 글자 크기 조절', const FontSliderPage()),
      ('랜덤 명언 표시기', const QuotePage()),
    ];

    return Scaffold(
      appBar: AppBar(title: const Text('메뉴')),
      body: ListView.separated(
        itemCount: items.length,
        separatorBuilder: (_, __) => const Divider(height: 1),
        itemBuilder: (context, i) {
          final (title, page) = items[i];
          return ListTile(
            title: Text(title),
            trailing: const Icon(Icons.chevron_right),
            onTap: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (_) => page),
            ),
          );
        }, ),
    );
  }
}