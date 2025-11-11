import 'package:flutter/material.dart';

import 'memo_list_page.dart';
import 'memo_service.dart';

void main() => runApp(const MemoApp());

class MemoApp extends StatelessWidget {
  const MemoApp({super.key});

  static const String baseUrl = "http://localhost/api/memos";

  @override
  Widget build(BuildContext context) {
    final service = MemoService(baseUrl: baseUrl);

    return MaterialApp(
      title: '메모장',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: MemoListPage(service: service),
    );
  }
}