import 'package:flutter/material.dart';

import 'api_service.dart';
import 'book_list_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    final api = ApiService('http://localhost');
    return MaterialApp(
      title: 'Book App',
      theme: ThemeData(useMaterial3: true),
      home: BookListPage(api: api),
    );
  }
}