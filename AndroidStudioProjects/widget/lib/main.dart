import 'package:flutter/material.dart';
//실습파일 import
import 'input_page.dart';
import 'colorbutton_page.dart';
import 'counter_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: const MainMenu(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MainMenu extends StatelessWidget {
  const MainMenu({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("기본 위젯 실습")),
      // 실습주제, 실습파일의 클래스 이름
      body: Column(children: [_menuItem(context, "카운트 버튼", const CounterPage())]),
    );
  }

  Widget _menuItem(BuildContext context, String title, Widget page) {
    return ElevatedButton(
        onPressed: () =>
            Navigator.push(context, MaterialPageRoute(builder: (_) => page)),
        child: Text(title),
    );
  }
}